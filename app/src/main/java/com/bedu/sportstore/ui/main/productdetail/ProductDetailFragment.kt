package com.bedu.sportstore.ui.main.productdetail

import android.content.Intent
import android.os.Build
import com.bedu.sportstore.ui.fragments.main.FormaPagoFragment
import java.util.Date

import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.bedu.sportstore.R
import com.bedu.sportstore.core.broadcast.CartCounterReceiver
import com.bedu.sportstore.databinding.FragmentDetailProductBinding
import com.bedu.sportstore.db.CarritoProducto
import com.bedu.sportstore.db.DataBase
import com.bedu.sportstore.model.Categoria
import com.bedu.sportstore.model.Producto
import com.bedu.sportstore.ui.main.productdetail.adapter.ProductDetailAdapter
import com.bedu.sportstore.ui.main.productos_categoria.ProductosCategoriaFragment
import com.bedu.sportstore.utileria.UserSession
import com.bedu.sportstore.utileria.UtilFragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class ProductDetailFragment : Fragment(R.layout.fragment_detail_product),
    ProductDetailAdapter.OnProductoClickListener {

    private var categoria: Categoria = Categoria()
    //private var producto: Producto? = null
    private lateinit var binding : FragmentDetailProductBinding
    private var nuevoElementoCarrito:CarritoProducto? = null
    private var idcategoria: Int = 0
    private var idproducto: Int = 0

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

        categoria = DataBase.categorias.find { it.id == idcategoria }!!
        val productoSeleccionado = DataBase.productos.find { it.id == idproducto }
        binding.toolBarFragment.title = productoSeleccionado?.nombre
        binding.toolBarFragment.setNavigationIcon(R.drawable.ic_arrow_back) // need to set the icon here to have a navigation icon. You can simple create an vector image by "Vector Asset" and using here
        binding.toolBarFragment.setNavigationOnClickListener {

            if (it.id == -1) UtilFragment().replaceFragmetnMain(
                requireActivity().supportFragmentManager,
                ProductosCategoriaFragment.newInstance(categoria)
            )
        }

        //ToolbarBasic().show((activity as AppCompatActivity?)!!, "Detalle Producto", false)
        Glide.with(view.context).load(productoSeleccionado?.imagen).into(binding.imgProducto);
        binding.nombreProducto.text = productoSeleccionado?.nombre
        binding.descripcionProducto.text = productoSeleccionado?.descripcion
        binding.precioProducto.text ="$ ${productoSeleccionado?.precio.toString()}"
        binding.descripcionLargaProducto.text = productoSeleccionado?.descripcionLarga
        binding.buttonFinalizarCompra.setOnClickListener{finalizarCompra()}
        binding.buttonAnadirCarrito.setOnClickListener{annadirCarrito(productoSeleccionado)}
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance(producto: Int, categoria: Int) =
            ProductDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt("idProducto", producto)
                    putInt("idCategoria", categoria)
                }
            }

    }

    private fun annadirCarrito(producto: Producto?){
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
    private fun finalizarCompra(){
        requireActivity().supportFragmentManager.commit {
            replace(R.id.frame_Layout, FormaPagoFragment())
            addToBackStack("formaPagoFragment")
        }
    }

    override fun onProductoClick(producto: Producto) {
        annadirCarrito(producto)
    }

}

