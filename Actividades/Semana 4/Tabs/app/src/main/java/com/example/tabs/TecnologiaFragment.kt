package com.example.tabs

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class TecnologiaFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container:
    ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tecnologias, container, false)
        val tvTecnologias = view.findViewById<TextView>(R.id.tvTecnologias)
        tvTecnologias.text = "- Kotlin\n- Python\n- PHP\n- C#\n- JavaScript\n- HTML\n- SQL"

        view.findViewById<Button>(R.id.btnContactame).setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("Correo Electronico:")
                putExtra(Intent.EXTRA_EMAIL,
                    arrayOf("guerrerodavid42069@gmail.com"))
            }
            startActivity(intent)
        }
        return view
    }
}
