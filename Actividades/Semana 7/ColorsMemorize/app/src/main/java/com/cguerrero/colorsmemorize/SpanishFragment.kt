package com.cguerrero.colorsmemorize

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cguerrero.colorsmemorize.data.Color
import com.cguerrero.colorsmemorize.data.ColorAdapter

class SpanishFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ColorAdapter
    private val colorList = mutableListOf<Color>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_spanish, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        colorList.add(Color("Amarillo", "A-ma-ri-llo", R.drawable.yellow, R.raw.amarillo_sound))
        colorList.add(Color("Azul", "A-zul", R.drawable.blue, R.raw.azul_sound))
        colorList.add(Color("Blanco", "Blan-co", R.drawable.white, R.raw.blanco_sound))
        colorList.add(Color("Café", "Ca-fé", R.drawable.brown, R.raw.cafe_sound))
        colorList.add(Color("Celeste", "Ce-les-te", R.drawable.light_blue, R.raw.celeste_sound))
        colorList.add(Color("Gris", "Gris", R.drawable.gray, R.raw.gris_sound))
        colorList.add(Color("Morado", "Mo-ra-do", R.drawable.purple, R.raw.morado_sound))
        colorList.add(Color("Naranja", "Na-ran-ja", R.drawable.orange, R.raw.naranja_sound))
        colorList.add(Color("Negro", "Ne-gro", R.drawable.black, R.raw.negro_sound))
        colorList.add(Color("Rojo", "Ro-jo", R.drawable.red, R.raw.rojo_sound))
        colorList.add(Color("Rosa", "Ro-sa", R.drawable.pink, R.raw.rosa_sound))
        colorList.add(Color("Verde", "Ver-de", R.drawable.green, R.raw.verde_sound))

        adapter = ColorAdapter(requireContext(), colorList)
        recyclerView.adapter = adapter

        return view
    }
}
