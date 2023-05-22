package com.bedu.sportstore.ui.fragments.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
<<<<<<< HEAD
import androidx.core.view.get
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResult
=======
import androidx.appcompat.app.AppCompatActivity
>>>>>>> 096aae082334ae8bd77e66635127b9f2ac66807b
import androidx.recyclerview.widget.LinearLayoutManager
import com.bedu.sportstore.Home
import com.bedu.sportstore.R
import com.bedu.sportstore.Settings
import com.bedu.sportstore.databinding.FragmentDetailProductBinding
import com.bedu.sportstore.databinding.FragmentProductosCategoriaBinding
import com.bedu.sportstore.db.Categoria
import com.bedu.sportstore.db.DataBase
import com.bedu.sportstore.db.Producto
import com.bedu.sportstore.ui.adapters.ProductoCategoriaAdapter
<<<<<<< HEAD
import kotlin.math.log
=======
import com.bedu.sportstore.ui.toolbar.ToolbarBasic
import com.bedu.sportstore.utileria.UtilFragment
>>>>>>> 096aae082334ae8bd77e66635127b9f2ac66807b

class ProductosCategoriaFragment : Fragment(R.layout.fragment_productos_categoria),
    ProductoCategoriaAdapter.OnProductoClickListener {

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

        val categoria = DataBase.categorias.find { it.id == idCategoria?.toInt() }
        binding.toolBarFragment.title = categoria?.nombre?.uppercase()
        binding.toolBarFragment.setNavigationIcon(R.drawable.ic_arrow_back) // need to set the icon here to have a navigation icon. You can simple create an vector image by "Vector Asset" and using here
        binding.toolBarFragment.setNavigationOnClickListener {

            if (it.id == -1) UtilFragment().replaceFragmetnMain(
                requireActivity().supportFragmentManager,
                Home()
            )
        }

        val productos = DataBase.productos.filter { it.categoriaId == idCategoria?.toInt() }

        binding.rvProductosCategoria.setHasFixedSize(true)
        binding.rvProductosCategoria.layoutManager = LinearLayoutManager(view.context)
        binding.rvProductosCategoria.adapter =
            ProductoCategoriaAdapter(productos, this@ProductosCategoriaFragment)

    }

    override fun onProductoClick(producto: Producto) {
<<<<<<< HEAD
        Toast.makeText(context, "ProductosCategoriaFragment -> onProductoClick", Toast.LENGTH_SHORT).show()
        Log.i("Detail", producto.toString())
        val detailFragment = ProductDetailFragment()
        parentFragmentManager.commit {
            replace(R.id.frame_Layout, ProductDetailFragment.newInstance(producto))
        }
=======
        Toast.makeText(context, "ProductosCategoriaFragment -> onProductoClick", Toast.LENGTH_SHORT)
            .show()
>>>>>>> 096aae082334ae8bd77e66635127b9f2ac66807b
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