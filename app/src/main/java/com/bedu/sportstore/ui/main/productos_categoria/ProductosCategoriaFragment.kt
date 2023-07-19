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
import com.bedu.sportstore.model.Producto
import com.bedu.sportstore.ui.main.productos_categoria.adapter.ProductoCategoriaAdapter

class ProductosCategoriaFragment : Fragment(R.layout.fragment_productos_categoria),
    ProductoCategoriaAdapter.OnProductoClickListener {

    private lateinit var binding: FragmentProductosCategoriaBinding
    private var idCategoria: Int? = null
    private var nombreCategoria: String? = null
    private var categoria = Categoria()

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
        binding.toolBarFragment.title =
            nombreCategoria?.uppercase() //categoria?.nombre?.uppercase()
        binding.toolBarFragment.setNavigationIcon(R.drawable.ic_arrow_back) // need to set the icon here to have a navigation icon. You can simple create an vector image by "Vector Asset" and using here
        binding.toolBarFragment.setNavigationOnClickListener {

            if (it.id == -1)  findNavController().navigate(R.id.action_productosCategoriaFragment_to_homeFragment)
        }

        val productos = DataBase.productos.filter { it.categoriaId == idCategoria }

        binding.rvProductosCategoria.setHasFixedSize(true)
        binding.rvProductosCategoria.layoutManager = LinearLayoutManager(view.context)
        binding.rvProductosCategoria.adapter =
            ProductoCategoriaAdapter(productos, this@ProductosCategoriaFragment)

    }

    override fun onProductoClick(producto: Producto) {
        val action = ProductosCategoriaFragmentDirections
            .actionProductosCategoriaFragmentToProductDetailFragment(producto.id, producto.categoriaId)
        findNavController().navigate(action)
        /*parentFragmentManager.commit {
            replace(
                R.id.frame_Layout,
                ProductDetailFragment.newInstance(producto.id, idCategoria ?: 0)
            )
        }*/

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