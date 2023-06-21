package com.bedu.sportstore.ui.main.productos_categoria

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.bedu.sportstore.ui.main.home.HomeFragment
import com.bedu.sportstore.R
import com.bedu.sportstore.databinding.FragmentProductosCategoriaBinding
import com.bedu.sportstore.db.DataBase
import com.bedu.sportstore.model.Categoria
import com.bedu.sportstore.model.Producto
import com.bedu.sportstore.ui.main.productdetail.ProductDetailFragment
import com.bedu.sportstore.ui.main.productos_categoria.adapter.ProductoCategoriaAdapter
import com.bedu.sportstore.utileria.UtilFragment

class ProductosCategoriaFragment : Fragment(R.layout.fragment_productos_categoria),
    ProductoCategoriaAdapter.OnProductoClickListener {

    private lateinit var binding: FragmentProductosCategoriaBinding
    private var idCategoria: String? = null
    private var nombreCategoria: String? = null
    private var categoria = Categoria()

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

        categoria = DataBase.categorias.find { it.id == idCategoria?.toInt() } ?: Categoria()
        binding.toolBarFragment.title = categoria?.nombre?.uppercase()
        binding.toolBarFragment.setNavigationIcon(R.drawable.ic_arrow_back) // need to set the icon here to have a navigation icon. You can simple create an vector image by "Vector Asset" and using here
        binding.toolBarFragment.setNavigationOnClickListener {

            if (it.id == -1) UtilFragment().replaceFragmetnMain(
                requireActivity().supportFragmentManager,
                HomeFragment()
            )
        }

        val productos = DataBase.productos.filter { it.categoriaId == idCategoria?.toInt() }

        binding.rvProductosCategoria.setHasFixedSize(true)
        binding.rvProductosCategoria.layoutManager = LinearLayoutManager(view.context)
        binding.rvProductosCategoria.adapter =
            ProductoCategoriaAdapter(productos, this@ProductosCategoriaFragment)

    }

    override fun onProductoClick(producto: Producto) {
        Log.i("Detail", producto.toString())
        val detailFragment = ProductDetailFragment()
        parentFragmentManager.commit {
            replace(R.id.frame_Layout, ProductDetailFragment.newInstance(producto, categoria ))
        }

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