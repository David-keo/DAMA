package com.example.tinderwork

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.tinderwork.model.Professional

class DetailsActivity : AppCompatActivity() {

    private lateinit var imgDetail: ImageView
    private lateinit var txtName: TextView
    private lateinit var txtProfession: TextView
    private lateinit var btnCall: Button
    private lateinit var btnWhatsApp: Button
    private lateinit var txtExperience: TextView
    private lateinit var txtAbout : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        imgDetail = findViewById(R.id.imgDetailsProfessional)
        txtName = findViewById(R.id.txtDetailsName)
        txtProfession = findViewById(R.id.txtDetailsProfession)
        txtExperience = findViewById(R.id.txtDetailsExperience)
        txtAbout = findViewById(R.id.txtDetailsAboutDescription)
        btnCall = findViewById(R.id.btnCall)
        btnWhatsApp = findViewById(R.id.btnWhatsApp)

        val professional = intent.getSerializableExtra("PROFESSIONAL") as? Professional

        professional?.let { prof ->
            txtName.text = prof.pName
            txtProfession.text = prof.pProfession
            txtExperience.text = prof.pExperience
            txtAbout.text = prof.pAbout
            Glide.with(this).load(prof.pImage).into(imgDetail)

            btnCall.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${prof.pPhone}"))
                startActivity(intent)
            }

            btnWhatsApp.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/${prof.pPhone}"))
                startActivity(intent)
            }
        } ?: run {
        }
    }
}
