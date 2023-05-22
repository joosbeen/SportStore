package com.bedu.sportstore.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bedu.sportstore.R
import com.bedu.sportstore.db.Producto
import com.bumptech.glide.Glide

class ProductDetailAdapter(
    private val producto: Producto,
    private val onProductoClick: OnProductoClickListener
) : RecyclerView.Adapter<ProductDetailAdapter.ViewHolder>() {


    interface
    OnProductoClickListener {
        fun onProductoClick(producto: Producto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductDetailAdapter.ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_producto_categoria, parent, false)

        return ProductDetailAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductDetailAdapter.ViewHolder, position: Int) {
        val producto = producto
        holder.bind(producto)

        holder.itemView.setOnClickListener {
            onProductoClick.onProductoClick(producto)
        }
    }

    override fun getItemCount(): Int = 1

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val imgProducto: ImageView = view.findViewById(R.id.imgProducto)
        private val nombreProducto: TextView = view.findViewById(R.id.nombreProducto)
        private val precioProducto: TextView = view.findViewById(R.id.precioProducto)
        private val descripcionProducto: TextView = view.findViewById(R.id.descripcionProducto)
        private val context: Context = view.context

        fun bind(producto: Producto) {

            nombreProducto.text = producto.nombre
            precioProducto.text = "$ ${producto.precio.toString()} MXN"
            descripcionProducto.text = producto.descripcion

            Glide.with(context).load(producto.imagen).into(imgProducto);

        }
    }

}