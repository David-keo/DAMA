package com.jfagoaga.puellaagenda

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jfagoaga.puellaagenda.classes.Configs
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    private lateinit var editTextName: EditText
    private lateinit var recyclerViewContact: RecyclerView
    private lateinit var adapter: ContactAdapter
    private val contacts = mutableListOf<Contact>()
    companion object {
        private const val ADD_CONTACT_REQUEST_CODE = 1
        const val VIEW_CONTACT_REQUEST_CODE = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Toolbar como ActionBar
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        editTextName = findViewById(R.id.editTextNombre)
        recyclerViewContact = findViewById(R.id.recyclerViewContact)
        val buttonSearch = findViewById<Button>(R.id.buttonBuscar)
        val buttonAdd = findViewById<Button>(R.id.buttonAgregar)

        adapter = ContactAdapter(contacts, this)
        recyclerViewContact.layoutManager = LinearLayoutManager(this)
        recyclerViewContact.adapter = adapter

        buttonSearch.setOnClickListener {
            val filter = editTextName.text.toString()
            if (filter.isNotEmpty()) {
                searchContact(filter)
            } else {
                loadContacts()
            }
        }
        buttonAdd.setOnClickListener {
            val intent = Intent(this, AddContactActivity::class.java)
            startActivityForResult(intent, ADD_CONTACT_REQUEST_CODE)
        }


        loadContacts()
    }

    // Mostrar contactos
    private fun loadContacts() {
        val queue = Volley.newRequestQueue(this)
        val url = "${Configs.URL_WEBSERVICES}contacts_api.php"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("API_RESPONSE", "Load Contacts: $response") // Log para debugging
                try {
                    val jsonArray = JSONArray(response)
                    contacts.clear()
                    for (i in 0 until jsonArray.length()) {
                        val contact = jsonArray.getJSONObject(i)
                        val id_contact = contact.getString("id_contact").toInt()
                        val contact_name = contact.getString("contact_name")
                        val contact_phone = contact.getString("contact_phone")
                        contacts.add(Contact(id_contact, contact_name, contact_phone))
                    }
                    adapter.notifyDataSetChanged()
                } catch (e: Exception) {
                    showToast("Error al parsear respuesta: ${e.message}")
                    e.printStackTrace()
                }
            },
            { error ->
                showToast("Error de conexión: ${error.message}")
                error.printStackTrace()
            }
        )
        queue.add(stringRequest)
    }

    //buscar contactos
    private fun searchContact(filter: String) {
        val queue = Volley.newRequestQueue(this)
        val url = "${Configs.URL_WEBSERVICES}contacts_api.php?search=$filter"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("API_RESPONSE", "Search Contacts: $response") // Log para debugging
                try {
                    val jsonArray = JSONArray(response)
                    contacts.clear()
                    for (i in 0 until jsonArray.length()) {
                        val contact = jsonArray.getJSONObject(i)
                        val id_contact = contact.getString("id_contact").toInt()
                        val contact_name = contact.getString("contact_name")
                        val contact_phone = contact.getString("contact_phone")
                        contacts.add(Contact(id_contact, contact_name, contact_phone))
                    }
                    adapter.notifyDataSetChanged()
                    if (jsonArray.length() == 0) {
                        showToast("No se encontraron contactos")
                    }
                } catch (e: Exception) {
                    showToast("Error al parsear respuesta: ${e.message}")
                    e.printStackTrace()
                }
            },
            { error ->
                showToast("Error de conexión: ${error.message}")
                error.printStackTrace()
            }
        )
        queue.add(stringRequest)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((requestCode == ADD_CONTACT_REQUEST_CODE || requestCode == VIEW_CONTACT_REQUEST_CODE)
            && resultCode == RESULT_OK) {
            loadContacts()
        }
    }
}

data class Contact(val id_contact: Int, val contact_name: String, val contact_phone: String)

class ContactAdapter(

    private val contacts: List<Contact>,
    private val context: AppCompatActivity
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewContact: TextView = itemView.findViewById(R.id.textViewContacto)
        val textViewPhone: TextView = itemView.findViewById(R.id.textViewTelefono)
        val buttonViewContact: Button = itemView.findViewById(R.id.buttonVerContacto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.textViewContact.text = contact.contact_name
        holder.textViewPhone.text = contact.contact_phone

        holder.buttonViewContact.setOnClickListener {
            val intent = Intent(context, ContactDetailsActivity::class.java).apply {
                putExtra("id_contact", contact.id_contact)
                putExtra("contact_name", contact.contact_name)
                putExtra("contact_phone", contact.contact_phone)
            }
            context.startActivityForResult(intent, MainActivity.VIEW_CONTACT_REQUEST_CODE)
        }
    }

    override fun getItemCount(): Int = contacts.size
}