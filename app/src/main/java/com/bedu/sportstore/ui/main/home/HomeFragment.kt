package com.bedu.sportstore.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bedu.sportstore.R
import com.bedu.sportstore.databinding.FragmentHomeBinding
import com.bedu.sportstore.model.response.CategoriaResponse
import com.bedu.sportstore.repository.remote.SportStoreHttp
import com.bedu.sportstore.ui.main.home.adapter.CategoriaAdapter
import com.bedu.sportstore.utileria.Utility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment(R.layout.fragment_home), CategoriaAdapter.OnCategoriaClickListener {

    private lateinit var binding: FragmentHomeBinding
    private val categoriaService by lazy { SportStoreHttp.categoriaHttp() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        binding.listCategories.setHasFixedSize(true)
        binding.listCategories.layoutManager = LinearLayoutManager(view.context)
        cargarCategorias()
    }

    private fun cargarCategorias() {
        categoriaService.getAll().enqueue(object : Callback<List<CategoriaResponse>> {
            override fun onResponse(call: Call<List<CategoriaResponse>>, response: Response<List<CategoriaResponse>>) {
                if (response.isSuccessful) {
                    response.body()?.let { categorias ->
                        binding.listCategories.adapter = CategoriaAdapter(categorias, this@HomeFragment)
                    }
                }
            }
            override fun onFailure(call: Call<List<CategoriaResponse>>, t: Throwable) {
                Utility.displaySnackBar(requireView(), getString(R.string.msg_error_cargar_categoria), requireContext(), R.color.red)
            }
        })
    }

    override fun oncategoriaClick(categoria: CategoriaResponse) {
        val action = HomeFragmentDirections.actionHomeFragmentToProductosCategoriaFragment(
            categoria.id,
            categoria.nombre
        )
        findNavController().navigate(action)
    }

}