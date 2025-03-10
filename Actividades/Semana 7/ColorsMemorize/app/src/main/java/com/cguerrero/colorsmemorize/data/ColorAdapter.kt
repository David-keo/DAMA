package com.cguerrero.colorsmemorize.data

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cguerrero.colorsmemorize.DetailActivity
import com.cguerrero.colorsmemorize.R
import com.cguerrero.colorsmemorize.data.Color

class ColorAdapter(
    private val context: Context,
    private val colorList: List<Color>
) : RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_color, parent, false)
        return ColorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val color = colorList[position]
        holder.colorImage.setImageResource(color.imageResId)
        holder.colorName.text = color.name

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("color", color)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return colorList.size
    }

    class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val colorImage: ImageView = itemView.findViewById(R.id.colorImage)
        val colorName: TextView = itemView.findViewById(R.id.colorName)
    }
}
