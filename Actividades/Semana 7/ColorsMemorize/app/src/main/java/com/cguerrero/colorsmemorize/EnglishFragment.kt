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

class EnglishFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ColorAdapter
    private val colorList = mutableListOf<Color>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_english, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        colorList.add(Color("Yellow", "YÉ-loh", R.drawable.yellow, R.raw.yellow_sound))
        colorList.add(Color("Blue", "BLÚ", R.drawable.blue, R.raw.blue_sound))
        colorList.add(Color("White", "WÁIT", R.drawable.white, R.raw.white_sound))
        colorList.add(Color("Brown", "BRAUN", R.drawable.brown, R.raw.brown_sound))
        colorList.add(Color("Light Blue", "LÁIT BLÚ", R.drawable.light_blue, R.raw.light_blue_sound))
        colorList.add(Color("Gray", "GRÉI", R.drawable.gray, R.raw.gray_sound))
        colorList.add(Color("Purple", "PÉR-pol", R.drawable.purple, R.raw.purple_sound))
        colorList.add(Color("Orange", "Ó-rinch", R.drawable.orange, R.raw.orange_sound))
        colorList.add(Color("Black", "BLAK", R.drawable.black, R.raw.black_sound))
        colorList.add(Color("Red", "RED", R.drawable.red, R.raw.red_sound))
        colorList.add(Color("Pink", "PINK", R.drawable.pink, R.raw.pink_sound))
        colorList.add(Color("Green", "GRÍN", R.drawable.green, R.raw.green_sound))

        adapter = ColorAdapter(requireContext(), colorList)
        recyclerView.adapter = adapter

        return view
    }
}
