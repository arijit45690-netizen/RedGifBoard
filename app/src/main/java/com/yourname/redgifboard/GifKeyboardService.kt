package com.yourname.redgifboard

import android.content.ClipDescription
import android.inputmethodservice.InputMethodService
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.core.view.inputmethod.InputConnectionCompat
import androidx.core.view.inputmethod.InputContentInfoCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import java.io.File
import java.net.URL

class GifKeyboardService : InputMethodService() {

    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private lateinit var gifAdapter: GifAdapter
    private var authToken: String = ""
    private var currentDownloadJob: Job? = null

    private var currentPage = 1
    private var currentQuery = "trending"
    private var isLoading = false

    override fun onCreateInputView(): View {
        val view = layoutInflater.inflate(R.layout.keyboard_view, null)

        val recyclerView = view.findViewById<RecyclerView>(R.id.gifGrid)
        val searchBar = view.findViewById<EditText>(R.id.searchBar)
        val loadingBar = view.findViewById<ProgressBar>(R.id.loadingBar)
        val statusText = view.findViewById<TextView>(R.id.statusText)
        val loadMoreBtn = view.findViewById<Button>(R.id.loadMoreBtn)

        val tagKiss = view.findViewById<Button>(R.id.tagKiss)
        val tagSexy = view.findViewById<Button>(R.id.tagSexy)
        val tagAnime = view.findViewById<Button>(R.id.tagAnime)
        val tagCosplay = view.findViewById<Button>(R.id.tagCosplay)

        gifAdapter = GifAdapter { gif -> sendGif(gif, loadingBar, statusText) }
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = gifAdapter

        searchBar.setOnClickListener {
            searchBar.requestFocus()
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(searchBar, InputMethodManager.SHOW_IMPLICIT)
        }

        serviceScope.launch {
            loadingBar.visibility = View.VISIBLE
            statusText.text = "Loading trending GIFs..."
            fetchToken()
            loadGifs(currentQuery, loadingBar, statusText)
        }

        searchBar.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = searchBar.text.toString().trim()

                if (query.isNotEmpty()) {
                    currentQuery = query
                    currentPage = 1

                    serviceScope.launch {
                        statusText.text = "Searching..."
                        loadGifs(query, loadingBar, statusText)
                    }
                }
                true
            } else {
                false
            }
        }

        loadMoreBtn.setOnClickListener {
            if (!isLoading) {
                currentPage++

                serviceScope.launch {
                    loadGifs(currentQuery, loadingBar, statusText, true)
                }
            }
        }

        fun quickSearch(tag: String) {
            searchBar.setText(tag)
            currentQuery = tag
            currentPage = 1

            serviceScope.launch {
                loadGifs(tag, loadingBar, statusText)
            }
        }

        tagKiss.setOnClickListener { quickSearch("kiss") }
        tagSexy.setOnClickListener { quickSearch("sexy") }
        tagAnime.setOnClickListener { quickSearch("anime") }
        tagCosplay.setOnClickListener { quickSearch("cosplay") }

        return view
    }

    private suspend fun fetchToken() {
        try {
            val response = withContext(Dispatchers.IO) {
                RedGifsClient.api.getToken()
            }
            authToken = response.token
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun loadGifs(
        query: String,
        loadingBar: ProgressBar,
        statusText: TextView,
        append: Boolean = false
    ) {
        if (authToken.isEmpty()) {
            fetchToken()
        }

        try {
            isLoading = true
            loadingBar.visibility = View.VISIBLE

            val response = withContext(Dispatchers.IO) {
                RedGifsClient.api.searchGifs(
                    "Bearer $authToken",
                    query,
                    20,
                    currentPage
                )
            }

            if (append) {
                gifAdapter.addGifs(response.gifs)
            } else {
                gifAdapter.updateGifs(response.gifs)
            }

            statusText.text = if (response.gifs.isEmpty()) {
                "No results found"
            } else {
                ""
            }

        } catch (e: Exception) {
            statusText.text = "Error loading GIFs"
            e.printStackTrace()
        } finally {
            loadingBar.visibility = View.GONE
            isLoading = false
        }
    }

    private fun sendGif(gif: GifItem, loadingBar: ProgressBar, statusText: TextView) {
        val ic = currentInputConnection ?: return
        val editorInfo = currentInputEditorInfo ?: return

        currentDownloadJob?.cancel()

        currentDownloadJob = serviceScope.launch {
            statusText.text = "Sending GIF..."
            loadingBar.visibility = View.VISIBLE

            try {
                val cacheFile = withContext(Dispatchers.IO) {
                    val file = File(cacheDir, "${gif.id}.gif")

                    if (!file.exists()) {
                        URL(gif.urls.sd).openStream().use { input ->
                            file.outputStream().use { output ->
                                input.copyTo(output)
                            }
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
                    ic,
                    editorInfo,
                    inputContentInfo,
                    InputConnectionCompat.INPUT_CONTENT_GRANT_READ_URI_PERMISSION,
                    null
                )

                statusText.text = "✓ Sent!"
                delay(1200)
                statusText.text = ""

            } catch (e: Exception) {
                statusText.text = "Failed to send GIF"
                e.printStackTrace()
            } finally {
                loadingBar.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}
