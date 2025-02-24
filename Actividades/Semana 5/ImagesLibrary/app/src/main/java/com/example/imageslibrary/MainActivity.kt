package com.example.imageslibrary

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageSlider = findViewById<ImageSlider>(R.id.imageSlider)
        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel(R.drawable.op1, "¡Celebración!"))
        imageList.add(SlideModel(R.drawable.op2, "Carteles de busqueda"))
        imageList.add(SlideModel(R.drawable.op3, "Dibujo especial Manga"))
        imageList.add(SlideModel(R.drawable.op4, "Isla de las sirenas"))
        imageList.add(SlideModel(R.drawable.op5, "Dibujo especial volumen Wano 1"))
        imageList.add(SlideModel(R.drawable.op6, "COMBATE WANO"))

        imageSlider.setImageList(imageList)

        val btnWebSite = findViewById<Button>(R.id.btnWebSite)
        btnWebSite.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://one-piece.com/index.html"))
            startActivity(intent)
        }
    }
}