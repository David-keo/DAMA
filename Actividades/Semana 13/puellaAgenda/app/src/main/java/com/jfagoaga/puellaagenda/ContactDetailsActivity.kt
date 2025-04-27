package com.jfagoaga.puellaagenda

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.jfagoaga.puellaagenda.classes.Configs
import org.json.JSONObject

class ContactDetailsActivity : AppCompatActivity() {

    private lateinit var textViewName: TextView
    private lateinit var textViewPhone: TextView
    private lateinit var buttonEdit: Button
    private lateinit var buttonDelete: Button

    private var contactId: Int = 0
    private var contactName: String = ""
    private var contactPhone: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_details)

        contactId = intent.getIntExtra("id_contact", 0)
        contactName = intent.getStringExtra("contact_name") ?: ""
        contactPhone = intent.getStringExtra("contact_phone") ?: ""

        textViewName = findViewById(R.id.textViewDetailName)
        textViewPhone = findViewById(R.id.textViewDetailPhone)
        buttonEdit = findViewById(R.id.buttonEditContact)
        buttonDelete = findViewById(R.id.buttonDeleteContact)

        textViewName.text = contactName
        textViewPhone.text = contactPhone

        buttonEdit.setOnClickListener { showEditDialog() }
        buttonDelete.setOnClickListener { showDeleteConfirmation() }
    }

    private fun showEditDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_contact, null)
        val editTextName = dialogView.findViewById<EditText>(R.id.editTextEditName)
        val editTextPhone = dialogView.findViewById<EditText>(R.id.editTextEditPhone)

        editTextName.setText(contactName)
        editTextPhone.setText(contactPhone)

        AlertDialog.Builder(this)
            .setTitle("Editar Contacto")
            .setView(dialogView)
            .setPositiveButton("Guardar") { _, _ ->
                val newName = editTextName.text.toString().trim()
                val newPhone = editTextPhone.text.toString().trim()

                if (newName.isEmpty() || newPhone.isEmpty()) {
                    Toast.makeText(this, "Nombre y teléfono son requeridos", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                updateContact(newName, newPhone)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun showDeleteConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Confirmar Eliminación")
            .setMessage("¿Estás seguro de que quieres eliminar a $contactName?")
            .setPositiveButton("Eliminar") { _, _ ->
                deleteContact()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun updateContact(newName: String, newPhone: String) {
        val queue = Volley.newRequestQueue(this)
        val url = "${Configs.URL_WEBSERVICES}contacts_api.php"

        val params = HashMap<String, Any>()
        params["id_contact"] = contactId
        params["contact_name"] = newName
        params["contact_phone"] = newPhone

        val jsonObject = JSONObject(params as Map<*, *>)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.PUT, url, jsonObject,
            { response ->
                try {
                    if (response.has("message")) {
                        Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show()
                        contactName = newName
                        contactPhone = newPhone
                        textViewName.text = newName
                        textViewPhone.text = newPhone

                        setResult(RESULT_OK)
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error al procesar respuesta", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            },
            { error ->
                Toast.makeText(this, "Error al actualizar contacto: ${error.message}", Toast.LENGTH_SHORT).show()
                error.printStackTrace()
            }
        )

        queue.add(jsonObjectRequest)
    }

    private fun deleteContact() {
        val queue = Volley.newRequestQueue(this)
        val url = "${Configs.URL_WEBSERVICES}contacts_api.php?id=$contactId"

        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.DELETE, url, null,
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
                Toast.makeText(this, "Error al eliminar contacto: ${error.message}", Toast.LENGTH_SHORT).show()
                error.printStackTrace()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                return headers
            }
        }

        queue.add(jsonObjectRequest)
    }
}