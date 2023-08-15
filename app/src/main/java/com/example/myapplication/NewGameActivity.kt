package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.myapplication.story_data.loadStoryFromJson
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset

class NewGameActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)

        val chapters = loadStoryFromJson(applicationContext, "story.json")
        val chapterToLoad = chapters[0]

        val chapterTextView = findViewById<TextView>(R.id.chapterTextView)

        // Make the chapterTextView visible before starting the animation
        chapterTextView.visibility = View.VISIBLE

        // Animate the chapterTextView from the top and apply fade-in animation
        val slideAndFadeAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down)
        chapterTextView.startAnimation(slideAndFadeAnimation)

        // Make the chapterTextView visible before starting the animation
        chapterTextView.visibility = View.VISIBLE

        // Set the chapter information in the TextView
        chapterTextView.text =
            "Chapter Number: ${chapterToLoad.chapterNumber}\n" + "Chapter Title: ${chapterToLoad.chapterTitle}\n" + "Chapter Text: ${chapterToLoad.chapterText}"

        // Show the buttons layout with a slide-up animation
        val buttonLayout = findViewById<LinearLayout>(R.id.buttonLayout)
        val slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        buttonLayout.startAnimation(slideUpAnimation)

        // Set click listeners for the buttons
        val btn1 = findViewById<Button>(R.id.btn1)
        val btn2 = findViewById<Button>(R.id.btn2)
        val btn3 = findViewById<Button>(R.id.btn3)

        btn1.setOnClickListener {
            // Handle button 1 click
            // Perform the action associated with button 1
        }

        btn2.setOnClickListener {
            // Handle button 2 click
            // Perform the action associated with button 2
        }

        btn3.setOnClickListener {
            // Handle button 3 click
            // Perform the action associated with button 3
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        finish()
        // Apply transition animation
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }


    private fun loadJsonAndSaveItIntoWidgets() {
        // Load the JSON file from the assets folder
        val jsonString: String = loadJSONFromAsset("player_data.json")

        // Parse the JSON string into a JSONObject
        val jsonObject = JSONObject(jsonString)

        // Access the values from the JSON object
        val aboutTitle = jsonObject.getJSONObject("player_info").getString("player_name")
        val aboutText = jsonObject.getJSONObject("player_info").getString("race")
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
}