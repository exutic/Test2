package com.example.myapplication

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var tvWelcome: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvWelcome = findViewById(R.id.tvWelcome)
    }

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
            val name = etName.text.toString()
            tvWelcome.text = "Welcome, $name!"
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
    }

    fun continueGame(view: View) {
        // Handle "Continue" button click
        val intent = Intent(this, NewGameActivity::class.java)
        startActivity(intent)
    }

    fun openAboutScreen(view: View) {
        // Handle "About" button click
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }

    fun exitGame(view: View) {
        // Handle "Exit Game" button click
        finishAffinity() // Close the application
    }
}