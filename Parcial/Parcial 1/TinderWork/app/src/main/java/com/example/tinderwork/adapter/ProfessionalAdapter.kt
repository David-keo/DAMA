package com.example.tinderwork.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.tinderwork.R
import com.example.tinderwork.model.Professional
import com.example.tinderwork.DetailsActivity

class ProfessionalAdapter(private val context: Context, private var professionalList: List<Professional>) :
    RecyclerView.Adapter<ProfessionalAdapter.ViewHolder>() {

    class ViewHolder(view: View, val context: Context) : RecyclerView.ViewHolder(view) {
        val imgProfessional: ImageView = view.findViewById(R.id.imgProfessional)
        val txtName: TextView = view.findViewById(R.id.txtName)
        val txtProfession: TextView = view.findViewById(R.id.txtProfession)
        val txtExperience: TextView = view.findViewById(R.id.txtExperience)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_professional, parent, false)
        return ViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val professional = professionalList[position]

        holder.txtName.text = professional.pName
        holder.txtProfession.text = professional.pProfession
        holder.txtExperience.text = "Años de experiencia: ${professional.pExperience}"

        Glide.with(holder.context)
            .load(professional.pImage)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.imgProfessional)

        holder.itemView.setOnClickListener {
            if (professional != null) {
                val intent = Intent(holder.context, DetailsActivity::class.java)
                intent.putExtra("PROFESSIONAL", professional)
                holder.context.startActivity(intent)
            } else {
                Toast.makeText(holder.context, "Error al cargar los detalles del profesional", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount() = professionalList.size

    fun updateList(newList: List<Professional>) {
        professionalList = newList
        notifyDataSetChanged()
    }
}
