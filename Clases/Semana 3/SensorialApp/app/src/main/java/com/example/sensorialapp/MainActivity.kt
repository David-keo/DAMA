package com.example.sensorialapp

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var miSensor: Sensor? = null // Hacer que miSensor sea nullable
    private lateinit var administradorDeSensores: SensorManager
    private lateinit var disparadorEventoSensor: SensorEventListener
    private lateinit var lblValor: TextView
    private lateinit var btnValor: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lblValor = findViewById(R.id.lblValorProximidad)
        btnValor = findViewById(R.id.btnValor)
        // Inicializar mi sensor
        administradorDeSensores = getSystemService(SENSOR_SERVICE) as
                SensorManager
        miSensor =
            administradorDeSensores.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        if (miSensor == null) {
            Toast.makeText(this, "Su dispositivo no tiene el sensor de proximidad", Toast.LENGTH_LONG).show()
                    finish()
        } else {
            Toast.makeText(this, "Sensor de Proximidad detectado",
                Toast.LENGTH_LONG).show()
        }
        disparadorEventoSensor = object : SensorEventListener {
            override fun onSensorChanged(sensorEvent: SensorEvent) {
                // Imprimir el valor del sensor para ver cómo está cambiando
                val valorProximidad = sensorEvent.values[0]
                Log.d("SensorProximidad", "Valor recibido: ${sensorEvent.values.joinToString()}")
                // Mostrar el valor del sensor en el TextView
                lblValor.text = "Valor del sensor: $valorProximidad"
                // Comprobar el rango máximo del sensor
                val rangoMaximo = miSensor?.maximumRange ?: 0f
                Log.d("SensorProximidad", "Rango máximo del sensor: $rangoMaximo")
                // Verifica si el objeto está cerca o lejos del sensor
                if (valorProximidad < rangoMaximo) {
                // Se ha acercado al sensor
                    btnValor.setBackgroundColor(Color.RED)
                    btnValor.text = "Se ha acercado al sensor!"
                } else {
                    btnValor.setBackgroundColor(Color.GREEN)
                    btnValor.text = "Se ha alejado del sensor!"
                }
            }
            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
            }
        }
        iniciarSensor()
    }
    private fun iniciarSensor() {
        miSensor?.let { sensor ->
            administradorDeSensores.registerListener(disparadorEventoSensor,
                sensor, SensorManager.SENSOR_DELAY_UI)
        }
    }
    private fun detenerSensor() {
        administradorDeSensores.unregisterListener(disparadorEventoSensor)
    }
    override fun onPause() {
        detenerSensor()
        super.onPause()
    }
    override fun onResume() {
        iniciarSensor()
        super.onResume()
    }
}