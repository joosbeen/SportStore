package com.bedu.sportstore.ui.fragments.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bedu.sportstore.R
import com.bedu.sportstore.Settings
import com.bedu.sportstore.databinding.FragmentProductosCategoriaBinding
import com.bedu.sportstore.db.Categoria
import com.bedu.sportstore.db.DataBase
import com.bedu.sportstore.db.Producto
import com.bedu.sportstore.ui.adapters.ProductoCategoriaAdapter
import kotlin.math.log

class ProductosCategoriaFragment : Fragment(R.layout.fragment_productos_categoria),  ProductoCategoriaAdapter.OnProductoClickListener{

    private lateinit var binding: FragmentProductosCategoriaBinding
    private var idCategoria: String? = null
    private var nombreCategoria: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idCategoria = it.getString("idCategoria")
            nombreCategoria = it.getString("nombreCategoria")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductosCategoriaBinding.bind(view)

        val productos = DataBase.productos.filter { it.categoriaId == idCategoria?.toInt() }

        binding.rvProductosCategoria.setHasFixedSize(true)
        binding.rvProductosCategoria.layoutManager = LinearLayoutManager(view.getContext())
        binding.rvProductosCategoria.adapter = ProductoCategoriaAdapter(productos, this@ProductosCategoriaFragment)

    }

    override fun onProductoClick(producto: Producto) {
        Toast.makeText(context, "ProductosCategoriaFragment -> onProductoClick", Toast.LENGTH_SHORT).show()
        Log.i("Entrando en el producto", 1.toString())

    }
    companion object {
        @JvmStatic
        fun newInstance(categoria: Categoria) =
            ProductosCategoriaFragment().apply {
                arguments = Bundle().apply {
                    putString("idCategoria", categoria.id.toString())
                    putString("nombreCategoria", categoria.nombre)
                }
            }
    }
}