package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class NewGameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        finish()
        // Apply transition animation
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}