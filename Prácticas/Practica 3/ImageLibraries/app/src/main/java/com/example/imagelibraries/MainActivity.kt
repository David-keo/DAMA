package com.example.imagelibraries

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Lista de imágenes y descripciones
        val images = listOf(
            R.drawable.img1 to "DarkSouls: Remasterd",
            R.drawable.img2 to "DarkSouls II: Scholar of the first Sin",
            R.drawable.img3 to "DarkSouls III",
            R.drawable.img4 to "Elden Ring",
            R.drawable.img5 to "Elden Ring: NighTreign"
        )
        // Configurar ViewPager2
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val adapter = ImagePagerAdapter(images)
        viewPager.adapter = adapter
    }
}