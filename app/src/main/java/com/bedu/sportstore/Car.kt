package com.bedu.sportstore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bedu.sportstore.databinding.FragmentCarBinding
import com.bedu.sportstore.db.Favorite
import com.bedu.sportstore.ui.adapters.FavoriteAdapter

class Car : Fragment() {

    private var _binding: FragmentCarBinding? = null
    private val binding get() = _binding!!

    val favoriteList = listOf(
        Favorite("0000000001", "12/12/12","$200 MXN", "Entregado"),
        Favorite("0000000002", "11/11/11","$199 MXN", "Pendiente"),
        Favorite("0000000003", "10/10/10","$198 MXN", "Entregado")
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Inflate the layout for this fragment
        initRecycler()
    }
    private fun initRecycler(){
        binding.rvFavorite.layoutManager = LinearLayoutManager(requireContext())
        val adapter = FavoriteAdapter(favoriteList)
        binding.rvFavorite.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCarBinding.inflate(inflater, container, false)
        return binding.root
    }
}