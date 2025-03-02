package com.example.tinderwork

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinderwork.adapter.ProfessionalAdapter
import com.example.tinderwork.model.Professional

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerProfessionals: RecyclerView
    private lateinit var adapter: ProfessionalAdapter
    private lateinit var edtSearch: EditText
    private lateinit var professionalList: List<Professional>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtSearch = findViewById(R.id.edtSearch)
        recyclerProfessionals = findViewById(R.id.recyclerProfessional)
        recyclerProfessionals.layoutManager = LinearLayoutManager(this)

        professionalList = listOf(
            Professional("Lic. José Martínez", "Abogado", "10 años", R.drawable.abogado_jose, "jose@example.com", "00000000", "Especialista en derecho penal y derecho familiar, con experiencia en casos de alto perfil."),
            Professional("Carlos Gómez", "Bombero", "7 años", R.drawable.bombero_carlos, "carlosbombero@example.com", "00000000", "Bombero con experiencia en rescate y atención de emergencias, comprometido con la seguridad comunitaria."),
            Professional("Oficial Ana Rodríguez", "Policía", "6 años", R.drawable.policia_ana, "ana.policia@example.com", "00000000", "Policía con amplia experiencia en patrullaje, prevención de delitos y seguridad pública."),
            Professional("Dr. Luis Fernández", "Médico General", "12 años", R.drawable.dr_luis, "luis@example.com", "00000000", "Médico general con experiencia en atención primaria, diagnóstico y tratamiento de enfermedades comunes."),
            Professional("Arq. Gabriela Soto", "Arquitecta", "8 años", R.drawable.arq_gabriela, "gabriela@example.com", "00000000", "Arquitecta especializada en diseño urbano y proyectos residenciales, con enfoque en la sostenibilidad."),
            Professional("Dra. Marta López", "Veterinaria", "4 años", R.drawable.dra_marta, "marta.veterinaria@example.com", "00000000", "Veterinaria con experiencia en atención clínica de mascotas y cirugías menores, apasionada por el cuidado animal.")
        )

        adapter = ProfessionalAdapter(this, professionalList)
        recyclerProfessionals.adapter = adapter

        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterList(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterList(query: String) {
        val filteredList = professionalList.filter {
            it.pName.contains(query, ignoreCase = true) || it.pProfession.contains(query, ignoreCase = true)
        }
        adapter.updateList(filteredList)
    }
}
