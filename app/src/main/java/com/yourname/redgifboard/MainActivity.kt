package com.yourname.redgifboard

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnEnable = findViewById<Button>(R.id.btnEnable)
        val btnSelect = findViewById<Button>(R.id.btnSelect)

        // Step 1: Opens Android keyboard settings so user can enable our keyboard
        btnEnable.setOnClickListener {
            startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))
        }

        // Step 2: Shows the keyboard picker so user can switch to ours
        btnSelect.setOnClickListener {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showInputMethodPicker()
        }
    }

    override fun onResume() {
        super.onResume()
        updateButtonStates()
    }

    private fun updateButtonStates() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val isEnabled = imm.enabledInputMethodList.any {
            it.packageName == packageName
        }
        val step2 = findViewById<TextView>(R.id.step2Hint)
        step2.text = if (isEnabled)
            "✓ Keyboard enabled! Now tap Step 2 to switch to it."
        else
            "Tap Step 1 first to enable the keyboard in settings."
    }
}
