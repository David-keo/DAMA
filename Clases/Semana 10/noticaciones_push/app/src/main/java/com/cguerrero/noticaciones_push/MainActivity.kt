package com.cguerrero.noticaciones_push

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private lateinit var textoTokenGenerado: TextView
    private lateinit var botonObtenerToken: Button
    private lateinit var botonCopiarToken: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        FirebaseMessaging.getInstance().subscribeToTopic("general")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("FCM", "✅ Suscripción al tópico 'general' exitosa")
                } else {
                    Log.e("FCM", "❌ Error al suscribirse al tópico", task.exception)
                }
            }


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textoTokenGenerado = findViewById(R.id.txtTokenGenerado)
        botonObtenerToken = findViewById(R.id.botonObtenerToken)
        botonCopiarToken = findViewById(R.id.botonCopiarToken)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1000
                )
            }
        }

        botonObtenerToken.setOnClickListener {
            try {
                FirebaseMessaging.getInstance().token.addOnCompleteListener { tarea ->
                    if (tarea.isSuccessful) {
                        val token = tarea.result
                        textoTokenGenerado.text = token
                    } else {
                        textoTokenGenerado.text =
                            "Error al obtener el token: ${tarea.exception?.message}"
                        Log.e("FCM", "Error al obtener el token", tarea.exception)
                    }
                }
            } catch (e: Exception) {
                textoTokenGenerado.text = "Excepción: ${e.message}"
                Log.e("FCM", "Excepción al obtener el token", e)
            }
        }

        botonCopiarToken.setOnClickListener {
            val token = textoTokenGenerado.text.toString()
            if (token.isNotEmpty() && token != "TOKEN GENERADO" && !token.startsWith("Error") && !token.startsWith(
                    "Excepción"
                )
            ) {
                val portapapeles = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val datos = ClipData.newPlainText("Token FCM", token)
                portapapeles.setPrimaryClip(datos)
                Toast.makeText(this, "Token copiado al portapapeles", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Primero obtén un token válido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 👇 Esta función va FUERA del onCreate
    override fun onRequestPermissionsResult(
        codigoSolicitud: Int,
        permisos: Array<out String>,
        resultados: IntArray
    ) {
        super.onRequestPermissionsResult(codigoSolicitud, permisos, resultados)
        if (codigoSolicitud == 1000) {
            if (resultados.isNotEmpty() && resultados[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("FCM", "Permiso de notificaciones concedido")
            } else {
                Log.d("FCM", "Permiso de notificaciones denegado")
            }
        }
    }
}
