package com.bedu.sportstore.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bedu.sportstore.R
import com.bedu.sportstore.db.CarritoProducto
import com.bedu.sportstore.db.DataBase
import com.bedu.sportstore.model.entity.CarritoEntity
import com.bumptech.glide.Glide

class CarritoAdapter(
    private val carritoss: List<CarritoEntity>,
    private val onProductoClick: OnCartProductoClickListener
) : RecyclerView.Adapter<CarritoAdapter.ViewHolder>() {

    interface OnCartProductoClickListener {
        fun onCartProductoClick(carrito: CarritoEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_carrito, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = carritoss.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = carritoss[position]
        holder.bind(producto)
        holder.itemView.findViewById<ImageView>(R.id.cartIconDelete).setOnClickListener {
            onProductoClick.onCartProductoClick(producto)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val cartImagenProducto: ImageView = view.findViewById(R.id.cartImagenProducto)
        private val cartNombreProducto: TextView = view.findViewById(R.id.cartNombreProducto)
        private val cartDescripcionProducto: TextView = view.findViewById(R.id.cartDescripcionProducto)
        private val cartTallaColorProducto: TextView = view.findViewById(R.id.cartTallaColorProducto)
        private val cartPrecioProducto: TextView = view.findViewById(R.id.cartPrecioProducto)
        private val context: Context = view.context

        fun bind(cart: CarritoEntity) {
            //val producto = DataBase.productos.find { it.id == cart.productoId }
            cartNombreProducto.text = cart?.nombre
            cartDescripcionProducto.text = cart?.descripcion
            cartTallaColorProducto.text = "Talla: M"
            cartPrecioProducto.text = "$ ${cart?.precio} MXN"
            Glide.with(context).load(cart?.imagen).into(cartImagenProducto);
        }

    }
}