package com.bedu.sportstore.ui.frag

import com.bedu.sportstore.ui.fragments.main.FormaPagoFragment
import java.util.Date

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.bedu.sportstore.R
import com.bedu.sportstore.databinding.FragmentDetailProductBinding
import com.bedu.sportstore.db.CarritoProducto
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
    private var nuevoElementoCarrito:CarritoProducto? = null

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
        binding.buttonFinalizarCompra.setOnClickListener{finalizarCompra()}
        binding.buttonAnadirCarrito.setOnClickListener{annadirCarrito(productoSeleccionado[0])}
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

    private fun annadirCarrito(producto: Producto){
        Toast.makeText(context, "Se añadio el producto", Toast.LENGTH_SHORT)
            .show()
        Log.i("Añadir","Carrito "+ producto)
        val idNew=UserSession.user?.id
        //DataBase.carrito.add(Date().time,producto.id,UserSession.user?.id ?:0)

    }
    private fun finalizarCompra(){
        requireActivity().supportFragmentManager.commit {
            replace(R.id.frame_Layout, FormaPagoFragment())
            addToBackStack("formaPagoFragment")
        }
    }

    override fun onProductoClick(producto: Producto) {
        TODO("Not yet implemented")
        annadirCarrito(producto)
    }

}

