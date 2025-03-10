package com.cguerrero.uso_camara

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var btnTomarFoto: Button
    private lateinit var btnCompartirCorreo: ImageView
    private lateinit var btnCompartirWhatsapp: ImageView
    private lateinit var imgFoto: ImageView
    private lateinit var currentPhotoPath: String
    private var photoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnTomarFoto = findViewById(R.id.btnTomarFoto)
        btnCompartirCorreo = findViewById(R.id.btnCompartirCorreo)
        btnCompartirWhatsapp = findViewById(R.id.btnCompartirWhatsapp)
        imgFoto = findViewById(R.id.imgFoto)

        btnTomarFoto.setOnClickListener {
            solicitarPermisoCamara()
        }

        btnCompartirCorreo.setOnClickListener {
            compartirPorCorreo()
        }

        btnCompartirWhatsapp.setOnClickListener {
            compartirPorWhatsapp()
        }
    }

    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success && photoUri != null) {
            imgFoto.setImageURI(photoUri)
            Toast.makeText(this, "Foto tomada y guardada", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Error al tomar la foto", Toast.LENGTH_SHORT).show()
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            tomarFoto()
        } else {
            Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun solicitarPermisoCamara() {
        requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
    }

    private fun tomarFoto() {
        when {
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                val photoFile = crearArchivo()
                photoUri = FileProvider.getUriForFile(
                    this,
                    "com.cguerrero.uso_camara.fileprovider",
                    photoFile
                )
                currentPhotoPath = photoFile.absolutePath

                photoUri?.let { uri ->
                    takePictureLauncher.launch(uri)
                }
            }

            shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA) -> {
                Toast.makeText(this, "Se necesita el permiso de cámara para tomar fotos", Toast.LENGTH_LONG).show()
                requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
            }

            else -> {
                requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
            }
        }
    }

    private fun crearArchivo(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = getExternalFilesDir("Pictures") ?: filesDir
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    private fun compartirPorCorreo() {
        if (photoUri == null) {
            Toast.makeText(this, "Primero tome una foto antes de compartir", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "image/jpeg"
            putExtra(Intent.EXTRA_SUBJECT, "Mira esta foto")
            putExtra(Intent.EXTRA_TEXT, "Wacha esta foto que tome con la app.")
            putExtra(Intent.EXTRA_STREAM, photoUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        startActivity(Intent.createChooser(intent, "Enviar imagen por correo"))
    }

    private fun compartirPorWhatsapp() {
        if (photoUri == null) {
            Toast.makeText(this, "Primero tome una foto antes de compartir", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "image/jpeg"
            putExtra(Intent.EXTRA_TEXT, "Wacha esta foto que tome con la app.")
            putExtra(Intent.EXTRA_STREAM, photoUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            `package` = "com.whatsapp"
        }

        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "WhatsApp no está instalado", Toast.LENGTH_SHORT).show()
        }
    }
}
