package com.example.circlegame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val puntaje = intent.getIntExtra("PUNTAJE", 0)
        val completado = intent.getBooleanExtra("COMPLETADO", false)
        val nivel = intent.getIntExtra("NIVEL", 1)
        val textoResultado =
            findViewById<TextView>(R.id.resultadoText)
        val textoPuntaje = findViewById<TextView>(R.id.puntajeText)
        if (completado) {
            if (nivel > 2) {
                textoResultado.text = "¡Felicidades! Completaste el juego"
            } else {
                textoResultado.text = "¡Nivel superado!"
            }
        } else {
            textoResultado.text = "¡Perdiste!"
        }
        textoPuntaje.text = "Puntaje: $puntaje"
        findViewById<Button>(R.id.nuevoJuego).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}