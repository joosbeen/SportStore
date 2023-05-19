package com.bedu.sportstore.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bedu.sportstore.R
import com.bedu.sportstore.db.Categoria
import kotlin.math.log

class CategoriaAdapter(val categorias: MutableList<Categoria>) :
    RecyclerView.Adapter<CategoriaAdapter.ViewHolder>() {


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
        holder.itemView.findViewById<TextView>(R.id.txtCategoryRedirect).setOnClickListener {
            Toast.makeText(it.context, "Categoria ${categoria.nombre} lista en desarrollo!", Toast.LENGTH_SHORT).show()
            Log.d("jbenny", "onBindViewHolder: ${categoria.nombre}")
            // TODO: Falta enlazar Fragment lista de ropa
        }
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title: TextView = view.findViewById(R.id.txtCategoryTitle)
        val imagen: ImageView = view.findViewById(R.id.txtCategoryImage)
        val redirect: TextView = view.findViewById(R.id.txtCategoryRedirect)

        fun bind(item: Categoria) {
            title.text = item.nombre
            imagen.setImageResource(item.imagen)
            redirect.setOnClickListener { }
        }

    }
}