package com.bedu.sportstore.ui.main.productos_categoria

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bedu.sportstore.R
import com.bedu.sportstore.databinding.FragmentProductosCategoriaBinding
import com.bedu.sportstore.db.DataBase
import com.bedu.sportstore.model.Categoria
import com.bedu.sportstore.model.response.ProductoResponse
import com.bedu.sportstore.repository.remote.SportStoreHttp
import com.bedu.sportstore.ui.main.productos_categoria.adapter.ProductoCategoriaAdapter
import com.bedu.sportstore.utileria.Utility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductosCategoriaFragment : Fragment(R.layout.fragment_productos_categoria),
    ProductoCategoriaAdapter.OnProductoClickListener {

    private lateinit var binding: FragmentProductosCategoriaBinding
    private var idCategoria: Int? = null
    private var nombreCategoria: String? = null
    private var categoria = Categoria()
    private val productoHttp by lazy { SportStoreHttp.productoHttp() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idCategoria = it.getInt("idCategoria")
            nombreCategoria = it.getString("nombreCategoria")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductosCategoriaBinding.bind(view)

        categoria = DataBase.categorias.find { it.id == idCategoria } ?: Categoria()
        binding.toolBarFragment.title = nombreCategoria?.uppercase()

        binding.toolBarFragment.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.toolBarFragment.setNavigationOnClickListener {
            if (it.id == -1) findNavController().navigate(R.id.action_productosCategoriaFragment_to_homeFragment)
        }

        binding.rvProductosCategoria.setHasFixedSize(true)
        binding.rvProductosCategoria.layoutManager = LinearLayoutManager(view.context)

        productoHttp.getFindCategory(idCategoria!!).enqueue(
            object : Callback<List<ProductoResponse>> {
                override fun onResponse(
                    call: Call<List<ProductoResponse>>,
                    response: Response<List<ProductoResponse>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { productos ->
                            binding.rvProductosCategoria.adapter =
                                ProductoCategoriaAdapter(productos, this@ProductosCategoriaFragment)
                        }
                    }
                }

                override fun onFailure(call: Call<List<ProductoResponse>>, t: Throwable) {
                    Utility.displaySnackBar(
                        view,
                        getString(R.string.msg_error_cargar_producto),
                        requireContext(),
                        R.color.red
                    )
                }
            }
        )


    }

    override fun onProductoClick(producto: ProductoResponse) {
        val action = ProductosCategoriaFragmentDirections
            .actionProductosCategoriaFragmentToProductDetailFragment(
                producto.id,
                producto.categoria_id
            )
        findNavController().navigate(action)
    }

    companion object {
        @JvmStatic
        fun newInstance(categoria: Categoria) =
            ProductosCategoriaFragment().apply {
                arguments = Bundle().apply {
                    putInt("idCategoria", categoria.id)
                    putString("nombreCategoria", categoria.nombre)
                }
            }
    }
}