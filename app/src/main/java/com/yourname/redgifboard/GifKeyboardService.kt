package com.yourname.redgifboard

import android.content.ClipDescription
import android.inputmethodservice.InputMethodService
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.core.view.inputmethod.EditorInfoCompat
import androidx.core.view.inputmethod.InputConnectionCompat
import androidx.core.view.inputmethod.InputContentInfoCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import java.io.File
import java.net.URL

class GifKeyboardService : InputMethodService() {

    private val serviceScope = CoroutineScope(Dispatchers.Main + Job())
    private lateinit var gifAdapter: GifAdapter
    private var authToken: String = ""
    private var currentDownloadJob: Job? = null

    override fun onCreateInputView(): View {
        val view = layoutInflater.inflate(R.layout.keyboard_view, null)

        val recyclerView = view.findViewById<RecyclerView>(R.id.gifGrid)
        val searchBar = view.findViewById<EditText>(R.id.searchBar)
        val loadingBar = view.findViewById<ProgressBar>(R.id.loadingBar)
        val statusText = view.findViewById<TextView>(R.id.statusText)

        // Set up the GIF grid — 2 columns
        gifAdapter = GifAdapter { gif -> sendGif(gif, loadingBar, statusText) }
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = gifAdapter

        // On open: fetch token, then load trending GIFs
        serviceScope.launch {
            loadingBar.visibility = View.VISIBLE
            statusText.text = "Loading trending GIFs..."
            fetchToken()
            loadGifs("trending", loadingBar, statusText)
        }

        // Search when user taps the search/done button on keyboard
        searchBar.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = searchBar.text.toString().trim()
                if (query.isNotEmpty()) {
                    serviceScope.launch {
                        loadingBar.visibility = View.VISIBLE
                        statusText.text = "Searching..."
                        loadGifs(query, loadingBar, statusText)
                    }
                }
                true
            } else false
        }

        return view
    }

    // ── Auth ─────────────────────────────────────────────────────────────────

    private suspend fun fetchToken() {
        try {
            val response = withContext(Dispatchers.IO) { RedGifsClient.api.getToken() }
            authToken = response.token
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // ── Load GIFs ─────────────────────────────────────────────────────────────

    private suspend fun loadGifs(query: String, loadingBar: ProgressBar, statusText: TextView) {
        try {
            val response = withContext(Dispatchers.IO) {
                RedGifsClient.api.searchGifs("Bearer $authToken", query)
            }
            gifAdapter.updateGifs(response.gifs)
            loadingBar.visibility = View.GONE
            statusText.text = if (response.gifs.isEmpty()) "No results found" else ""
        } catch (e: Exception) {
            loadingBar.visibility = View.GONE
            statusText.text = "Error loading GIFs. Check connection."
            e.printStackTrace()
        }
    }

    // ── Send GIF ─────────────────────────────────────────────────────────────

    private fun sendGif(gif: GifItem, loadingBar: ProgressBar, statusText: TextView) {
        val ic = currentInputConnection ?: return
        val editorInfo = currentInputEditorInfo ?: return

        // Cancel any previous download
        currentDownloadJob?.cancel()

        currentDownloadJob = serviceScope.launch {
            statusText.text = "Sending GIF..."
            loadingBar.visibility = View.VISIBLE

            try {
                val cacheFile = withContext(Dispatchers.IO) {
                    val file = File(cacheDir, "${gif.id}.gif")
                    if (!file.exists()) {
                        URL(gif.urls.sd).openStream().use { input ->
                            file.outputStream().use { output -> input.copyTo(output) }
                        }
                    }
                    file
                }

                val contentUri = FileProvider.getUriForFile(
                    this@GifKeyboardService,
                    "${packageName}.fileprovider",
                    cacheFile
                )

                val inputContentInfo = InputContentInfoCompat(
                    contentUri,
                    ClipDescription("gif", arrayOf("image/gif")),
                    null
                )

                InputConnectionCompat.commitContent(
                    ic, editorInfo, inputContentInfo,
                    InputConnectionCompat.INPUT_CONTENT_GRANT_READ_URI_PERMISSION,
                    null
                )

                loadingBar.visibility = View.GONE
                statusText.text = "✓ Sent!"
                delay(1500)
                statusText.text = ""

            } catch (e: Exception) {
                loadingBar.visibility = View.GONE
                statusText.text = "Failed to send. Try again."
                e.printStackTrace()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}
