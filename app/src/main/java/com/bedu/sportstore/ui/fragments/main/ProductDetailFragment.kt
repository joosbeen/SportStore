package com.bedu.sportstore.ui.fragments.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bedu.sportstore.R
import com.bedu.sportstore.databinding.FragmentDetailProductBinding
import com.bedu.sportstore.db.DataBase
import com.bedu.sportstore.db.Producto
import com.bedu.sportstore.ui.adapters.ProductDetailAdapter
import com.bedu.sportstore.ui.toolbar.ToolbarBasic
import com.bedu.sportstore.utileria.UserSession
import com.bumptech.glide.Glide

class ProductDetailFragment : Fragment(R.layout.fragment_detail_product),
    ProductDetailAdapter.OnProductoClickListener {

    private var idProducto: String? = null
    private var nombreProducto: String? = null
    private lateinit var binding : FragmentDetailProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            idProducto = it.getString("idCategoria")
            nombreProducto = it.getString("nombreCategoria")
        }
        Log.i("Detalles","arguments" + arguments)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailProductBinding.bind(view)

        val productoSeleccionado = DataBase.productos.filter { it.id == idProducto?.toInt() }
        Log.i("onViewCreated","productoSeleccionado"+productoSeleccionado)

        ToolbarBasic().show((activity as AppCompatActivity?)!!, "Detalle Producto", false)
        Glide.with(view.context).load(productoSeleccionado[0].imagen).into(binding.imgProducto);
        binding.nombreProducto.text = productoSeleccionado[0].nombre
        binding.descripcionProducto.text = productoSeleccionado[0].descripcion
        binding.precioProducto.text ="$ ${productoSeleccionado[0].precio.toString()}"
        binding.descripcionLargaProducto.text = productoSeleccionado[0].descripcionLarga
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance(producto: Producto) =
            ProductDetailFragment().apply {
                arguments = Bundle().apply {
                    putString("idCategoria", producto.id.toString())
                    putString("nombreCategoria", producto.nombre)
                }
            }
    }

    override fun onProductoClick(producto: Producto) {
        TODO("Not yet implemented")
    }

}