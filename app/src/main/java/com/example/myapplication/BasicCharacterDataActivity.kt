package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.coroutineContext


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


    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "my_data_store")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_character_data)


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

        val submitButton = findViewById<Button>(R.id.submit_n_continue)
        submitButton.setOnClickListener {
//            runBlocking {
//                saveData()
//            }
            // Call the suspend function saveData inside a coroutine scope
            lifecycleScope.launch(Dispatchers.IO) {
                saveData()
            }

            //IO for background task
            //Main for Main Ui or Main thread task
        }

        val clearButton = findViewById<Button>(R.id.clear_all_fields)
        clearButton.setOnClickListener {
            clearFields()
        }

    }

    // Save data into DataStore
    private suspend fun saveData() {
        val characterName = characterNameEditText.text.toString()
        val charClass = classEditText.text.toString()
        val background = backgroundEditText.text.toString()
        // Add more data retrieval from other EditText elements as needed

        val dataStoreKeyCharacterName = stringPreferencesKey("character_name")
        val dataStoreKeyCharClass = stringPreferencesKey("character_class")
        val dataStoreKeyBackground = stringPreferencesKey("background")

        // Use the DataStore dataStore.edit() function to edit the preferences
        dataStore.edit { preferences ->
            preferences[dataStoreKeyCharacterName] = characterName
            preferences[dataStoreKeyCharClass] = charClass
            preferences[dataStoreKeyBackground] = background
            // Add more key-value pairs for other data as needed
        }
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

    // Load data from DataStore and set it to the TextView
    private suspend fun loadData() {
        val dataStoreKeyCharacterName = stringPreferencesKey("character_name")
        val characterName = dataStore.data.map { preferences ->
            preferences[dataStoreKeyCharacterName] ?: ""
        }

        // Update the UI in the main (UI) thread
        lifecycleScope.launch(Dispatchers.Main) {
//            myTextView.text = characterName
            // myTextView can be change to the desired TextView
        }
    }
}
