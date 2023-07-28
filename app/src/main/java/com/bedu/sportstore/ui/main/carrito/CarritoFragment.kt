package com.bedu.sportstore.ui.main.carrito

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bedu.sportstore.R
import com.bedu.sportstore.databinding.FragmentCarritoBinding
import com.bedu.sportstore.model.entity.CarritoEntity
import com.bedu.sportstore.repository.local.AppDatabaseRoom
import com.bedu.sportstore.ui.main.carrito.adapter.CarritoAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CarritoFragment : Fragment(R.layout.fragment_carrito),
    CarritoAdapter.OnCartProductoClickListener {

    private var productoId: Int = 0
    private var totalCosto: Float = 0f
    private lateinit var binding: FragmentCarritoBinding
    private val carritoDao by lazy { AppDatabaseRoom.getDatabase(requireContext()).carritoDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productoId = it.getInt("productoId")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCarritoBinding.bind(view)

        loadAdapter()
        binding.toolBarFragment.title = getString(R.string.title_carrito_de_compra)
        binding.cartBtnFinalizarCompra.setOnClickListener { openFragmetnFormaPago() }
    }

    private fun loadAdapter() {

        binding.cartRVContent.setHasFixedSize(true)
        binding.cartRVContent.layoutManager = LinearLayoutManager(context)

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val carrito = carritoDao.getAll()
                binding.cartRVContent.adapter = CarritoAdapter(carrito, this@CarritoFragment)
                carrito.forEach {
                    totalCosto += it.precio
                }
                binding.cartTotalCosto.text = "$ ${totalCosto} MXN"
                if (carrito.isEmpty()) {
                    binding.cartBtnFinalizarCompra.text = "Carrito Vacio"
                }
            }
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

    override fun onCartProductoClick(carrito: CarritoEntity) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                carritoDao.delete(carrito)
            }
            loadAdapter()
        }
    }
}