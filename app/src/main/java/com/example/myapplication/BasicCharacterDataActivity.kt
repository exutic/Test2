package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.data_store.DataStoreViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BasicCharacterDataActivity : AppCompatActivity() {
    private lateinit var characterNameEditText: EditText
    private lateinit var classEditText: EditText
    private lateinit var backgroundEditText: EditText
    private lateinit var raceEditText: EditText
    private lateinit var alignmentEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var heightEditText: EditText
    private lateinit var weightEditText: EditText
    private lateinit var hairColorEditText: EditText
    private lateinit var eyesColorEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_character_data)
        findViews()
        initClicks()

    }

    private fun initClicks() {
        val submitButton = findViewById<Button>(R.id.submit_n_continue)
        submitButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                saveData()
            }
            val intent = Intent(this, NewGameActivity::class.java)
            startActivity(intent)
            // Apply transition animation
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            //'IO' for background task
            //'Main' for Main Ui or Main thread task
        }

        val clearButton = findViewById<Button>(R.id.clear_all_fields)
        clearButton.setOnClickListener {
            clearFields()
        }

    }

    private fun findViews() {
        // Initialize the EditTexts
        characterNameEditText = findViewById(R.id.character_name_edit_text)
        classEditText = findViewById(R.id.class_edit_text)
        backgroundEditText = findViewById(R.id.background_edit_text)
        raceEditText = findViewById(R.id.race_edit_text)
        alignmentEditText = findViewById(R.id.alignment_edit_text)
        ageEditText = findViewById(R.id.age_edit_text)
        heightEditText = findViewById(R.id.height_edit_text)
        weightEditText = findViewById(R.id.weight_edit_text)
        hairColorEditText = findViewById(R.id.hair_color_edit_text)
        eyesColorEditText = findViewById(R.id.eyes_color_edit_text)
    }

    private fun clearFields() {
        // Clear all the EditText fields
        characterNameEditText.text.clear()
        classEditText.text.clear()
        backgroundEditText.text.clear()
        raceEditText.text.clear()
        alignmentEditText.text.clear()
        ageEditText.text.clear()
        heightEditText.text.clear()
        weightEditText.text.clear()
        hairColorEditText.text.clear()
        eyesColorEditText.text.clear()
    }

    private lateinit var viewModel: DataStoreViewModel

    companion object {
        val characterNameKey = stringPreferencesKey("character_name")
        val characterClassKey = stringPreferencesKey("character_class")
        val characterBackgroundKey = stringPreferencesKey("character_background")
        val characterRaceKey = stringPreferencesKey("character_race")
        val characterAlignmentKey = stringPreferencesKey("character_alignment")
        val characterAgeKey = stringPreferencesKey("character_age")
        val characterHeightKey = stringPreferencesKey("character_height")
        val characterWeightKey = stringPreferencesKey("character_weight")
        val characterHairKey = stringPreferencesKey("character_hair")
        val characterEyesKey = stringPreferencesKey("character_eyes")
    }

    private suspend fun saveData() {
        viewModel = ViewModelProvider(this)[DataStoreViewModel::class.java]
        val dataStore = viewModel.dataStore
        val characterName = characterNameEditText.text.toString()
        val charClass = classEditText.text.toString()
        val background = backgroundEditText.text.toString()
        val race = raceEditText.text.toString()
        val align = alignmentEditText.text.toString()
        val age = ageEditText.text.toString()
        val height = heightEditText.text.toString()
        val weight = weightEditText.text.toString()
        val hair = hairColorEditText.text.toString()
        val eyes = eyesColorEditText.text.toString()

        dataStore.edit { Preferences ->
            Preferences[characterNameKey] = characterName
            Preferences[characterClassKey] = charClass
            Preferences[characterBackgroundKey] = background
            Preferences[characterRaceKey] = race
            Preferences[characterAlignmentKey] = align
            Preferences[characterAgeKey] = age
            Preferences[characterHeightKey] = height
            Preferences[characterWeightKey] = weight
            Preferences[characterHairKey] = hair
            Preferences[characterEyesKey] = eyes
        }
    }


}
