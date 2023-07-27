package com.bedu.sportstore.ui.main.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bedu.sportstore.R
import com.bedu.sportstore.model.response.CategoriaResponse
import com.bumptech.glide.Glide

class CategoriaAdapter(val categorias: List<CategoriaResponse>, val oncategoriaClick: OnCategoriaClickListener) :
    RecyclerView.Adapter<CategoriaAdapter.ViewHolder>() {

    interface OnCategoriaClickListener {
        fun oncategoriaClick(categoria: CategoriaResponse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_categoria_cardview, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = categorias.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoria = categorias[position]
        holder.bind(categoria)
        holder.itemView.findViewById<CardView>(R.id.card_view).setOnClickListener {
            oncategoriaClick.oncategoriaClick(categoria)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val title: TextView = view.findViewById(R.id.txtCategoryTitle)
        private val image: ImageView = view.findViewById(R.id.categoryImage)
        private val context: Context = view.context

        fun bind(item: CategoriaResponse) {
            title.text = item.nombre
            Glide.with(context).load(item.imagen).into(image)
        }

    }
}