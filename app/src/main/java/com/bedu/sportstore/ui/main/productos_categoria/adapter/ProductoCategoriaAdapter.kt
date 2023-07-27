package com.bedu.sportstore.ui.main.productos_categoria.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bedu.sportstore.R
import com.bedu.sportstore.model.response.ProductoResponse
import com.bumptech.glide.Glide

class ProductoCategoriaAdapter(
    private val productos: List<ProductoResponse>,
    private val onProductoClick: OnProductoClickListener
) : RecyclerView.Adapter<ProductoCategoriaAdapter.ViewHolder>()  {

    interface
    OnProductoClickListener {
        fun onProductoClick(producto: ProductoResponse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_producto_categoria, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = productos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = productos[position]
        holder.bind(producto)

        holder.itemView.setOnClickListener {
            onProductoClick.onProductoClick(producto)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val imgProducto: ImageView = view.findViewById(R.id.imgProducto)
        private val nombreProducto: TextView = view.findViewById(R.id.nombreProducto)
        private val precioProducto: TextView = view.findViewById(R.id.precioProducto)
        private val descripcionProducto: TextView = view.findViewById(R.id.descripcionProducto)
        private val context: Context = view.context

        fun bind(producto: ProductoResponse) {
            Log.i("productoadapter", "bind: $producto")
            nombreProducto.text = producto.nombre
            precioProducto.text = "$ ${producto.precio} MXN"
            descripcionProducto.text = producto.descripcion.capitalize()
            Glide.with(context).load(producto.imagen).into(imgProducto)
        }

    }
}