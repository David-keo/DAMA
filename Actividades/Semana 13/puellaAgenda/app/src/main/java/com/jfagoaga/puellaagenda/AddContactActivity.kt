package com.jfagoaga.puellaagenda

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.jfagoaga.puellaagenda.classes.Configs
import org.json.JSONObject

class AddContactActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var buttonSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        editTextName = findViewById(R.id.editTextContactName)
        editTextPhone = findViewById(R.id.editTextContactPhone)
        buttonSave = findViewById(R.id.buttonSaveContact)

        buttonSave.setOnClickListener {
            val name = editTextName.text.toString().trim()
            val phone = editTextPhone.text.toString().trim()

            if (name.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Nombre y teléfono son requeridos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            createContact(name, phone)
        }
    }

    private fun createContact(name: String, phone: String) {
        val queue = Volley.newRequestQueue(this)
        val url = "${Configs.URL_WEBSERVICES}contacts_api.php"

        val params = HashMap<String, String>()
        params["contact_name"] = name
        params["contact_phone"] = phone

        val jsonObject = JSONObject(params as Map<*, *>)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            { response ->
                try {
                    if (response.has("message")) {
                        Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show()
                        setResult(RESULT_OK)
                        finish()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error al procesar respuesta", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            },
            { error ->
                Toast.makeText(this, "Error al crear contacto: ${error.message}", Toast.LENGTH_SHORT).show()
                error.printStackTrace()
            }
        )

        queue.add(jsonObjectRequest)
    }
}