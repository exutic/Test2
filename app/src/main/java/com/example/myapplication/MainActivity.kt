package com.example.myapplication

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.media.MediaPlayer
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var tvWelcome: TextView
    private lateinit var imgLogin: ImageView
    private lateinit var ll_menu: LinearLayout
    private lateinit var vibrator: Vibrator
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViews()
        // Start playing music
        mediaPlayer?.start()
    }

    private fun findViews() {
        tvWelcome = findViewById(R.id.tvWelcome)
        imgLogin = findViewById(R.id.imgLogin)
        ll_menu = findViewById<LinearLayout>(R.id.ll_menu_buttons)
        vibrator = ContextCompat.getSystemService(this, Vibrator::class.java) as Vibrator
        // Create and configure MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.piano_horror_silent_hill)
        mediaPlayer?.isLooping = true
    }

    override fun onDestroy() {
        super.onDestroy()
        // Release MediaPlayer resources
        mediaPlayer?.release()
        mediaPlayer = null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    fun openLoginDialog(view: View) {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater: LayoutInflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.dialog_login, null)
        dialogBuilder.setView(dialogView)

        val etName = dialogView.findViewById<EditText>(R.id.etName)

        dialogBuilder.setTitle("Login")
        dialogBuilder.setMessage("Enter your name:")
        dialogBuilder.setPositiveButton("Login") { dialogInterface: DialogInterface, _: Int ->
            //Set visibility of my login button(image_view) to gone
            imgLogin.visibility = View.GONE
            val name = etName.text.toString()
            tvWelcome.text = "Welcome, $name!"

            // Fade animation
            val fadeAnimation = AlphaAnimation(0f, 1f)
            fadeAnimation.duration = 3000
            ll_menu.startAnimation(fadeAnimation)

            //Set visibility of my menu layout to visible
            ll_menu.visibility = View.VISIBLE

            // Vibration effect
            val vibrationEffect = VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(vibrationEffect)

            dialogInterface.dismiss()
        }
        dialogBuilder.setNegativeButton("Cancel") { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }

        val alertDialog: AlertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    fun startNewGame(view: View) {
        // Handle "New Game" button click
        val intent = Intent(this, NewGameActivity::class.java)
        startActivity(intent)
        // Apply transition animation
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    fun continueGame(view: View) {
        // Handle "Continue" button click
//        val intent = Intent(this, NewGameActivity::class.java)
//        startActivity(intent)
    }

    fun openAboutScreen(view: View) {
        // Handle "About" button click
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
        // Apply transition animation
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    fun openSettingsScreen(view: View) {
        // Handle "About" button click
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
        // Apply transition animation
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    fun exitGame(view: View) {
        // Handle "Exit Game" button click
        finishAffinity() // Close the application
        // Apply transition animation
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}