package com.bedu.sportstore.ui.fragments.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bedu.sportstore.R
import com.bedu.sportstore.databinding.FragmentCarritoBinding
import com.bedu.sportstore.db.DataBase
import com.bedu.sportstore.ui.adapters.CarritoAdapter
import com.bedu.sportstore.ui.toolbar.ToolbarBasic
import com.bedu.sportstore.utileria.UserSession

class ProductDetailFragment : Fragment(R.layout.fragment_detail_product) {


    private var productoId: Int = 0
    private var totalCosto: Float = 0f
    private lateinit var binding: FragmentCarritoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCarritoBinding.bind(view)


        ToolbarBasic().show((activity as AppCompatActivity?)!!, "Detalle Producto", false)

        //loadAdapter()

    }
    /*private fun loadAdapter() {
        //val carrito = DataBase.carrito.filter { it.usuarioId == UserSession.user?.id }

        binding.cartRVContent.setHasFixedSize(true)
        binding.cartRVContent.layoutManager = LinearLayoutManager(context)
        binding.cartRVContent.adapter = CarritoAdapter(carrito, this@CarritoFragment)

        binding.cartBtnFinalizarCompra.setOnClickListener { openFragmetnFormaPago() }

        if (carrito.isEmpty()) {
            binding.cartBtnFinalizarCompra.text = "Carrito Vacio"
        }

    }*/


}