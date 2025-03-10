package com.cguerrero.uso_camara

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
    private lateinit var imgFoto: ImageView
    private lateinit var currentPhotoPath: String
    private var photoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnTomarFoto = findViewById(R.id.btnTomarFoto)
        imgFoto = findViewById(R.id.imgFoto)

        btnTomarFoto.setOnClickListener {
            solicitarPermisoCamara()
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

                // Corrección del smart cast
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
}