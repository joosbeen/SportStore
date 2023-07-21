package com.bedu.sportstore.ui.main.productdetail

import android.content.Intent
import android.os.Build
import java.util.Date

import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bedu.sportstore.R
import com.bedu.sportstore.core.broadcast.CartCounterReceiver
import com.bedu.sportstore.databinding.FragmentDetailProductBinding
import com.bedu.sportstore.db.CarritoProducto
import com.bedu.sportstore.db.DataBase
import com.bedu.sportstore.model.Categoria
import com.bedu.sportstore.model.response.ProductoResponse
import com.bedu.sportstore.repository.remote.SportStoreHttp
import com.bedu.sportstore.utileria.UserSession
import com.bedu.sportstore.utileria.Utility
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailFragment : Fragment(R.layout.fragment_detail_product) {

    private var categoria: Categoria = Categoria()
    private var producto: ProductoResponse? = null
    private lateinit var binding: FragmentDetailProductBinding
    private var idcategoria: Int = 0
    private var idproducto: Int = 0
    private val productoHttp by lazy { SportStoreHttp.productoHttp() }

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
        binding.toolBarFragment.title = producto?.nombre
        binding.toolBarFragment.setNavigationIcon(R.drawable.ic_arrow_back) // need to set the icon here to have a navigation icon. You can simple create an vector image by "Vector Asset" and using here

    }

    private fun cargarInfoProducto() {
        productoHttp.getFindId(idproducto).enqueue(
            object: Callback<ProductoResponse> {
                override fun onResponse(call: Call<ProductoResponse>, response: Response<ProductoResponse>) {
                    if(response.isSuccessful) {
                        response.body()?.let {
                            producto = it
                            Glide.with(requireView().context).load(it.imagen).into(binding.imgProducto);
                            binding.nombreProducto.text = it.nombre
                            binding.descripcionProducto.text = it.descripcion
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
        DataBase.carrito.add(
            CarritoProducto(
                Date().time,
                producto?.id ?: 0,
                UserSession.user?.id ?: 0
            )
        )
        view?.let {
            Snackbar.make(
                it,
                "Se agrego al carrito", Snackbar.LENGTH_SHORT
            ).show()
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

