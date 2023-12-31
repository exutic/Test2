package com.example.myapplication.loading_screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.main.MainActivity
import com.example.myapplication.R

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    private val splashTimeOut: Long = 1000 // Delay in milliseconds (3seconds)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

            // Apply transition animation
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

        }, splashTimeOut)
    }
}