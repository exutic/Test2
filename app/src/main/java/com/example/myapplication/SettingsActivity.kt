package com.example.myapplication

import android.gesture.GestureLibraries.fromRawResource
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import java.util.Random

class SettingsActivity : AppCompatActivity() {
    private lateinit var stopImageView: ImageView
    private lateinit var playImageView: ImageView
    private lateinit var nextImageView: ImageView

    private lateinit var font1TextView: TextView
    private lateinit var font2TextView: TextView
    private lateinit var font3TextView: TextView

    private lateinit var player: SimpleExoPlayer
    private lateinit var playerView: PlayerView
    private lateinit var  songArray: List<Uri>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        findViews()
        findMusic()
        onClicks()


    }

    private fun findViews() {
        // Initialize ImageViews
        stopImageView = findViewById(R.id.settings_img_stop)
        playImageView = findViewById(R.id.settings_img_play)
        nextImageView = findViewById(R.id.settings_img_next)
        // Initialize TextViews
        font1TextView = findViewById(R.id.settings_tv_font_1)
        font2TextView = findViewById(R.id.settings_tv_font_2)
        font3TextView = findViewById(R.id.settings_tv_font_3)
        // Initialize ExoPlayer
        player = SimpleExoPlayer.Builder(this)
            .setTrackSelector(DefaultTrackSelector(this))
            .build()

        playerView = findViewById(R.id.player_view)
        playerView.player = player
    }

    private fun findMusic() {
        val rawResourceUri1 = RawResourceDataSource.buildRawResourceUri(R.raw.horror_music_1)
        val rawResourceUri2 = RawResourceDataSource.buildRawResourceUri(R.raw.horror_music_2)
        val rawResourceUri3 = RawResourceDataSource.buildRawResourceUri(R.raw.piano_horror_silent_hill)

        songArray= listOf(rawResourceUri1, rawResourceUri2, rawResourceUri3)
    }

    private fun onClicks(){

        // Set click listeners
        stopImageView.setOnClickListener { handleStopClick() }
        playImageView.setOnClickListener { handlePlayClick() }
        nextImageView.setOnClickListener { handleNextClick() }


        // Set click listeners
        font1TextView.setOnClickListener { handleFontTextViewClick(font1TextView) }
        font2TextView.setOnClickListener { handleFontTextViewClick(font2TextView) }
        font3TextView.setOnClickListener { handleFontTextViewClick(font3TextView) }
    }

    private fun handleStopClick() {
        // Handle stop button click event
        Toast.makeText(this, "Stop clicked", Toast.LENGTH_SHORT).show()
        stopMusic()
    }

    private fun handlePlayClick() {
        // Handle play button click event
        Toast.makeText(this, "Play clicked", Toast.LENGTH_SHORT).show()
        playMusic(R.raw.horror_music_1)
    }

    private fun handleNextClick() {
        // Handle next button click event
        Toast.makeText(this, "Next clicked", Toast.LENGTH_SHORT).show()
        val random = Random().nextInt(3) + 1
        val randomSongUri = songArray[random - 1]

        player.stop()

        val mediaItem = MediaItem.fromUri(randomSongUri)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    private fun handleFontTextViewClick(textView: TextView) {
        // Update the text color of the clicked TextView
        textView.isSelected = true
        textView.setTextColor(ContextCompat.getColor(this, R.color.selected_font_color))

        // Reset the text color of the other TextViews
        when (textView.id) {
            R.id.settings_tv_font_1 -> {
                font2TextView.isSelected = false
                font2TextView.setTextColor(ContextCompat.getColor(this, R.color.default_font_color))
                font3TextView.isSelected = false
                font3TextView.setTextColor(ContextCompat.getColor(this, R.color.default_font_color))
            }
            R.id.settings_tv_font_2 -> {
                font1TextView.isSelected = false
                font1TextView.setTextColor(ContextCompat.getColor(this, R.color.default_font_color))
                font3TextView.isSelected = false
                font3TextView.setTextColor(ContextCompat.getColor(this, R.color.default_font_color))
            }
            R.id.settings_tv_font_3 -> {
                font2TextView.isSelected = false
                font2TextView.setTextColor(ContextCompat.getColor(this, R.color.default_font_color))
                font1TextView.isSelected = false
                font1TextView.setTextColor(ContextCompat.getColor(this, R.color.default_font_color))
            }
        }
    }


    private fun playMusic(rawResourceId: Int) {
        val rawResourceUri = RawResourceDataSource.buildRawResourceUri(rawResourceId)
        val mediaItem = MediaItem.fromUri(rawResourceUri)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    private fun stopMusic() {
        player.stop()
    }

    private fun playNextMusic(rawResourceId: Int) {
        player.stop()
        playMusic(rawResourceId)
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}