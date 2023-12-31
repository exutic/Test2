package com.example.myapplication.game

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.main.MainActivity.Companion.viewModel
import com.example.myapplication.main.NewGameActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.*
import java.nio.charset.Charset


class CharacterDataInputActivity : AppCompatActivity() {
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
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

            startNewGame()
        }

        val clearButton = findViewById<Button>(R.id.clear_all_fields)
        clearButton.setOnClickListener {
            clearFields()
        }

    }

    private fun findViews() {
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

        Log.d("saveData", "saveData: Agha Rassoool")

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


        Log.d("saveData", "saveData: Agha Rassoool")
        saveDataToJson()
    }

    private fun saveDataToJson() {
        val jsonString: String = loadJSONFromAsset("player_data.json")
        val jsonObject = JSONObject(jsonString)

        // Modify the JSON object with new values
        val playerInfo = jsonObject.getJSONObject("player_info")
        playerInfo.put("name", characterNameEditText.text.toString())
        playerInfo.put("class", classEditText.text.toString())
        playerInfo.put("background", backgroundEditText.text.toString())
        playerInfo.put("race", raceEditText.text.toString())
        playerInfo.put("alignment", alignmentEditText.text.toString())
        playerInfo.put("age", ageEditText.text.toString())
        playerInfo.put("height", heightEditText.text.toString())
        playerInfo.put("weight", weightEditText.text.toString())
        playerInfo.put("hair_color", hairColorEditText.text.toString())
        playerInfo.put("eyes_color", eyesColorEditText.text.toString())

        // Convert the modified JSON object back to a string
        val modifiedJsonString = jsonObject.toString()

        // Save the modified JSON string to a file
        saveJSONToFile("player_data.json", modifiedJsonString)
    }

    private fun saveJSONToFile(filename: String, jsonString: String) {
        try {
            val file = File(filesDir, filename)
            val fileOutputStream = FileOutputStream(file)
            val outputStreamWriter = OutputStreamWriter(fileOutputStream, Charset.defaultCharset())
            val bufferedWriter = BufferedWriter(outputStreamWriter)
            bufferedWriter.use {
                it.write(jsonString)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun loadJSONFromAsset(filename: String): String {
        return try {
            val inputStream = assets.open(filename)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            buffer.toString(Charset.defaultCharset())
        } catch (ex: IOException) {
            ex.printStackTrace()
            "{}" // Return an empty JSON object in case of an error
        }
    }

    private fun startNewGame() {
        val intent = Intent(this, NewGameActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}


