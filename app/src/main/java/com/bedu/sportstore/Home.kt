package com.bedu.sportstore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bedu.sportstore.db.Categoria
import com.bedu.sportstore.db.DataBase
import com.bedu.sportstore.ui.adapters.CategoriaAdapter
import com.bedu.sportstore.ui.fragments.main.ProductosCategoriaFragment


class Home : Fragment(), CategoriaAdapter.OnCategoriaClickListener {

    // Add RecyclerView member
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.listCarories)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(view.getContext())
        recyclerView.adapter = CategoriaAdapter(DataBase.categorias, this@Home)
        return view
    }

    override fun oncategoriaClick(categoria: Categoria) {

        requireActivity().supportFragmentManager.commit {
            replace(R.id.frame_Layout, ProductosCategoriaFragment.newInstance(categoria))
            addToBackStack("productosCategoriaFragment")
        }

    }

}