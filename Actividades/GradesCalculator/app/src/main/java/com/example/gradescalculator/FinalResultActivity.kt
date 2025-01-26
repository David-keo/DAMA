package com.example.gradescalculator

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FinalResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final_result)
        val studentNameTextView = findViewById<TextView>(R.id.studentNameTextView)
        val finalStatusTextView = findViewById<TextView>(R.id.finalStatusTextView)
        val calculateButton = findViewById<Button>(R.id.calculateButton)
        val exitButton = findViewById<Button>(R.id.exitButton)

        //Capturando los datos del view anterior
        val studentName = intent.getStringExtra("studentName")
        val finalAverage = intent.getDoubleExtra("finalAverage", 0.0)
        val minimumPassingGrade = intent.getDoubleExtra("minimumPassingGrade", 6.0)

        studentNameTextView.text = "Estudiante: $studentName"

        calculateButton.setOnClickListener {
            if (finalAverage >= minimumPassingGrade) { //If para determinar si el estudiante aprobó
                finalStatusTextView.text = "¡Aprobado!"
                finalStatusTextView.setTextColor(Color.GREEN)
            } else {
                val pointsNeeded = minimumPassingGrade - finalAverage
                finalStatusTextView.text =
                    "No Aprobado\nLe faltaron %.2f puntos para aprobar.".format(pointsNeeded)
                finalStatusTextView.setTextColor(Color.RED)
            }
        }

        exitButton.setOnClickListener { //botón para regresar al inicio
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
