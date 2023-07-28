package com.bedu.sportstore.ui.main.productdetail

import android.content.Intent
import android.os.Build

import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bedu.sportstore.R
import com.bedu.sportstore.core.broadcast.CartCounterReceiver
import com.bedu.sportstore.databinding.FragmentDetailProductBinding
import com.bedu.sportstore.model.Categoria
import com.bedu.sportstore.model.response.ProductoResponse
import com.bedu.sportstore.model.response.toProductoEntity
import com.bedu.sportstore.repository.local.AppDatabaseRoom
import com.bedu.sportstore.repository.remote.SportStoreHttp
import com.bedu.sportstore.utileria.Utility
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class ProductDetailFragment : Fragment(R.layout.fragment_detail_product) {

    private var categoria: Categoria = Categoria()
    private var producto: ProductoResponse? = null
    private lateinit var binding: FragmentDetailProductBinding
    private var idcategoria: Int = 0
    private var idproducto: Int = 0
    private val productoHttp by lazy { SportStoreHttp.productoHttp() }
    private val carritoDao by lazy { AppDatabaseRoom.getDatabase(requireContext()).carritoDao() }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            idproducto = it.getInt("idProducto")
            idcategoria = it.getInt("idCategoria")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailProductBinding.bind(view)

        cargarInfoProducto()
        onClickListener()
        binding.toolBarFragment.title = "Detalle del producto"
        binding.toolBarFragment.setNavigationIcon(R.drawable.ic_arrow_back)

    }

    private fun cargarInfoProducto() {
        productoHttp.getFindId(idproducto).enqueue(
            object : Callback<ProductoResponse> {
                override fun onResponse(
                    call: Call<ProductoResponse>,
                    response: Response<ProductoResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            producto = it
                            Glide.with(requireView().context).load(it.imagen)
                                .into(binding.imgProducto);
                            binding.nombreProducto.text = it.nombre.capitalize()
                            binding.descripcionProducto.text = it.descripcion.capitalize()
                            binding.precioProducto.text = "$ ${it.precio.toString()}"
                            binding.descripcionLargaProducto.text = it.descripcion_larga
                        }
                    }
                }

                override fun onFailure(call: Call<ProductoResponse>, t: Throwable) {
                    Utility.displaySnackBar(
                        requireView(),
                        getString(R.string.msg_error_cargar_product),
                        requireContext(),
                        R.color.red
                    )
                }
            }
        )
    }

    private fun onClickListener() {
        binding.toolBarFragment.setNavigationOnClickListener {
            if (it.id == -1) {
                val action =
                    ProductDetailFragmentDirections.actionProductDetailFragmentToProductosCategoriaFragment(
                        idcategoria,
                        categoria.nombre
                    )
                findNavController().navigate(action)
            }
        }
        binding.buttonFinalizarCompra.setOnClickListener { finalizarCompra() }
        binding.buttonAnadirCarrito.setOnClickListener {
            producto?.let {
                annadirCarrito(it)
            }
        }
    }

    private fun annadirCarrito(producto: ProductoResponse) {

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                carritoDao.insert(producto.toProductoEntity())
                view?.let {
                    Snackbar.make(
                        it,
                        "Se agrego al carrito", Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
        // #### AppComponent
        // #### Envía un broadcast para notificar al BroadcastReceiver
        // ####
        val intent = Intent(requireContext(), CartCounterReceiver::class.java)
        context?.sendBroadcast(intent)
    }

    private fun finalizarCompra() =
        findNavController().navigate(R.id.action_productDetailFragment_to_formaPagoFragment)

}

