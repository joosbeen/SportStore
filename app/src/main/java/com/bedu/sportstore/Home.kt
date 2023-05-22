package com.bedu.sportstore

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bedu.sportstore.databinding.FragmentHomeBinding
import com.bedu.sportstore.db.Categoria
import com.bedu.sportstore.db.DataBase
import com.bedu.sportstore.ui.adapters.CategoriaAdapter
import com.bedu.sportstore.ui.fragments.main.ProductosCategoriaFragment
import com.bedu.sportstore.utileria.UtilFragment


class Home : Fragment(R.layout.fragment_home), CategoriaAdapter.OnCategoriaClickListener {

    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        binding.listCarories.setHasFixedSize(true)
        binding.listCarories.layoutManager = LinearLayoutManager(view.context)
        binding.listCarories.adapter = CategoriaAdapter(DataBase.categorias, this@Home)
    }

    override fun oncategoriaClick(categoria: Categoria) {
        UtilFragment().replaceFragmetnMain(
            requireActivity().supportFragmentManager,
            ProductosCategoriaFragment.newInstance(categoria)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}