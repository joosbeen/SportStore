package com.bedu.sportstore.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bedu.sportstore.R
import com.bedu.sportstore.databinding.FragmentHomeBinding
import com.bedu.sportstore.db.DataBase
import com.bedu.sportstore.model.Categoria
import com.bedu.sportstore.ui.main.home.adapter.CategoriaAdapter


class HomeFragment : Fragment(R.layout.fragment_home), CategoriaAdapter.OnCategoriaClickListener {

    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        binding.listCarories.setHasFixedSize(true)
        binding.listCarories.layoutManager = LinearLayoutManager(view.context)
        binding.listCarories.adapter = CategoriaAdapter(DataBase.categorias, this@HomeFragment)
    }

    override fun oncategoriaClick(categoria: Categoria) {
        val action = HomeFragmentDirections.actionHomeFragmentToProductosCategoriaFragment(categoria.id, categoria.nombre)
        findNavController().navigate(action)
    }

}