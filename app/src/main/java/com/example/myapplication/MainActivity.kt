package com.example.myapplication

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.data_store.DataStoreViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var tvWelcome: TextView
    private lateinit var imgLogin: ImageView
    private lateinit var llMenu: LinearLayout
    private lateinit var vibrator: Vibrator
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var viewModel: DataStoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViews()
        // Start playing music
        mediaPlayer?.start()


//        viewModel = ViewModelProvider(this).get(DataStoreViewModel::class.java)
        viewModel = ViewModelProvider(this)[DataStoreViewModel::class.java]

    }

    private fun findViews() {
        tvWelcome = findViewById(R.id.tvWelcome)
        imgLogin = findViewById(R.id.imgLogin)
        llMenu = findViewById(R.id.ll_menu_buttons)
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
            llMenu.startAnimation(fadeAnimation)

            //Set visibility of my menu layout to visible
            llMenu.visibility = View.VISIBLE

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
        val intent = Intent(this, BasicCharacterDataActivity::class.java)
        startActivity(intent)
        // Apply transition animation
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    fun continueGame(view: View) {
        // Handle "Continue" button click
//        val intent = Intent(this, CharacterSelectActivity::class.java)
//        startActivity(intent)
        lifecycleScope.launch(Dispatchers.IO) {
            loadData()
        }
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

    private suspend fun loadData() {
        val dataStore = viewModel.dataStore
        val characterNameKey = stringPreferencesKey("character_name")
        val characterNameValue = dataStore.data.map { preferences ->
            preferences[characterNameKey] ?: "No Name"
        }
        val characterClassKey = stringPreferencesKey("character_class")
        val characterClassValue = dataStore.data.map { Preferences ->
            Preferences[characterClassKey] ?: "No Class"
        }
        val characterBackgroundKey = stringPreferencesKey("character_background")
        val characterBackgroundValue = dataStore.data.map { Preferences ->
            Preferences[characterBackgroundKey] ?: "No Background"
        }
        val characterRaceKey = stringPreferencesKey("character_race")
        val characterRaceValue = dataStore.data.map { Preferences ->
            Preferences[characterRaceKey] ?: "No Race"
        }

        val characterAlignmentKey = stringPreferencesKey("character_alignment")
        val characterAlignmentValue = dataStore.data.map { Preferences ->
            Preferences[characterAlignmentKey] ?: "No Alignment"
        }
        val characterAgeKey = stringPreferencesKey("character_age")
        val characterAgeValue = dataStore.data.map { Preferences ->
            Preferences[characterAgeKey] ?: "No Age"
        }

        val characterHeightKey = stringPreferencesKey("character_height")
        val characterHeightValue = dataStore.data.map { Preferences ->
            Preferences[characterHeightKey] ?: "No Height"
        }
        val characterWeightKey = stringPreferencesKey("character_weight")
        val weight = dataStore.data.map { Preferences ->
            Preferences[characterWeightKey] ?: "No Weight"
        }
        val characterHairKey = stringPreferencesKey("character_hair")
        val characterHairValue = dataStore.data.map { Preferences ->
            Preferences[characterHairKey] ?: "No Hair Color"
        }
        val characterEyesKey = stringPreferencesKey("character_eyes")
        val characterEyesValue = dataStore.data.map { Preferences ->
            Preferences[characterEyesKey] ?: "No Eye Color"
        }

        // Log the value emitted by the Flow
        characterEyesValue.collect { value ->
            Log.d("Load Data", "characterEyesValue: $value")
        }


        // Update the UI in the main (UI) thread
//        lifecycleScope.launch(Dispatchers.Main) {
//            characterNameTextView.text = characterNameValue.toString()
//            classTextView.text = characterClassValue.toString()
//            backgroundTextView.text = characterBackgroundValue.toString()
//            raceTextView.text = characterRaceValue.toString()
//            alignmentTextView.text = characterAlignmentValue.toString()
//            ageTextView.text = characterAgeValue.toString()
//            heightTextView.text = characterHeightValue.toString()
//            weightTextView.text = weight.toString()
//            hairColorTextView.text = characterHairValue.toString()
//            eyesColorTextView.text = characterEyesValue.toString()
//        }
    }
}