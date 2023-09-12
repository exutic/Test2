package com.example.myapplication.main

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.data_classes.ChapterModel
import com.example.myapplication.data_classes.loadStoryFromJson

@SuppressLint("SetTextI18n")
class NewGameActivity : AppCompatActivity() {
    private lateinit var chapterToLoad: ChapterModel
    private lateinit var chapterTextView: TextView
    private lateinit var slideAndFadeAnimation: Animation
    private lateinit var slideUpAnimation: Animation

    private lateinit var btn_choice_1: Button
    private lateinit var btn_choice_2: Button
    private lateinit var btn_choice_3: Button

    private lateinit var buttonLayout: LinearLayout

    private var levelCounter = 0

    private lateinit var chapters: List<ChapterModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)
        findViews()
        initWidgets()
        initAnimations()

        loadChapter(chapters, levelCounter)
    }

    private fun loadChapter(chapters: List<ChapterModel>, index: Int) {
        chapterToLoad = chapters[index]

        chapterTextView.visibility = View.VISIBLE

        chapterTextView.startAnimation(slideAndFadeAnimation)
        chapterTextView.visibility = View.VISIBLE

        chapterTextView.text =
            "Chapter Number: ${chapterToLoad.chapterNumber}\n" +
                    "Chapter Title: ${chapterToLoad.chapterTitle}\n" +
                    "Chapter Text: ${chapterToLoad.chapterText}"

        chapterTextView.append("\n${chapterToLoad.task}\n")

        buttonLayout.startAnimation(slideUpAnimation)

        btn_choice_1.text = chapterToLoad.choice1
        btn_choice_2.text = chapterToLoad.choice2
        btn_choice_3.text = chapterToLoad.choice3
    }

    private fun findViews() {
        btn_choice_1 = findViewById<Button>(R.id.btn1)
        btn_choice_2 = findViewById<Button>(R.id.btn2)
        btn_choice_3 = findViewById<Button>(R.id.btn3)

        buttonLayout = findViewById<LinearLayout>(R.id.buttonLayout)

        chapterTextView = findViewById<TextView>(R.id.chapterTextView)

        chapters = loadStoryFromJson(applicationContext, "story.json")
    }

    private fun initWidgets() {
        btn_choice_1.setOnClickListener {
            println(btn_choice_1.text)
        }

        btn_choice_2.setOnClickListener {
            println(btn_choice_2.text)
        }

        btn_choice_3.setOnClickListener {
            println(btn_choice_3.text)
        }
    }

    private fun initAnimations() {
        slideAndFadeAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down)
        slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}