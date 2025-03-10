package com.cguerrero.colorsmemorize

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cguerrero.colorsmemorize.data.Color
import com.cguerrero.colorsmemorize.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val color = intent.getParcelableExtra<Color>("color")
        if (color == null) {
            finish()
            return
        }

        binding.detailImage.setImageResource(color.imageResId)
        binding.detailName.text = color.name
        binding.detailPronunciation.text = color.pronunciation

        binding.playButton.setOnClickListener {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this, color.audioResId)
            }
            mediaPlayer?.start()
        }

        binding.stopButton.setOnClickListener {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}