package com.bedu.sportstore.ui.fragments.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bedu.sportstore.R
import com.bedu.sportstore.databinding.FragmentCarritoBinding
import com.bedu.sportstore.db.CarritoProducto
import com.bedu.sportstore.db.DataBase
import com.bedu.sportstore.ui.adapters.CarritoAdapter
import com.bedu.sportstore.utileria.UserSession

class CarritoFragment : Fragment(R.layout.fragment_carrito),
    CarritoAdapter.OnCartProductoClickListener {

    private var productoId: Int = 0
    private var totalCosto: Float = 0f
    private lateinit var binding: FragmentCarritoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productoId = it.getInt("productoId")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCarritoBinding.bind(view)
        binding.toolBarFragment.title = getString(R.string.title_carrito_de_compra)
        loadAdapter()
    }

    private fun loadAdapter() {
        val carrito = DataBase.carrito.filter { it.usuarioId == UserSession.user?.id }
        totalCosto = 0f
        carrito.forEach { cp ->
            val prod = DataBase.productos.find { p -> p.id == cp.productoId }
            totalCosto += ((prod?.precio ?: 0) as Float)
        }

        binding.cartRVContent.setHasFixedSize(true)
        binding.cartRVContent.layoutManager = LinearLayoutManager(context)
        binding.cartRVContent.adapter = CarritoAdapter(carrito, this@CarritoFragment)

        binding.cartTotalCosto.text = "$ ${totalCosto} MXN"
        binding.cartBtnFinalizarCompra.setOnClickListener { openFragmetnFormaPago() }

        if (carrito.isEmpty()) {
            binding.cartBtnFinalizarCompra.text = "Carrito Vacio"
        }

    }

    private fun openFragmetnFormaPago() {
        if (totalCosto > 0)
            findNavController().navigate(R.id.action_carritoFragment_to_formaPagoFragment)
    }

    companion object {
        @JvmStatic
        fun newInstance(productoId: Int) =
            CarritoFragment().apply {
                arguments = Bundle().apply {
                    putInt("productoId", productoId)
                }
            }
    }

    override fun onCartProductoClick(carritoProducto: CarritoProducto) {
        val newCarrito = DataBase.carrito.filter { it.id != carritoProducto.id }
        DataBase.carrito = newCarrito as MutableList<CarritoProducto>
        loadAdapter()
    }
}