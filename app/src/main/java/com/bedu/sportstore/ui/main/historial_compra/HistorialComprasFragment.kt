package com.bedu.sportstore.ui.main.historial_compra

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bedu.sportstore.R
import com.bedu.sportstore.databinding.FragmentHistorialComprasBinding
import com.bedu.sportstore.db.DataBase
import com.bedu.sportstore.ui.adapters.HistorialCompraAdapter
import com.bedu.sportstore.ui.toolbar.ToolbarBasic
import com.bedu.sportstore.utileria.UserSession

class HistorialComprasFragment : Fragment(R.layout.fragment_historial_compras) {

    private lateinit var binding: FragmentHistorialComprasBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHistorialComprasBinding.bind(view)


        val toolbar =ToolbarBasic().show((activity as AppCompatActivity?)!!, "HISTORIAL DE COMPRA", false)
        toolbar.setNavigationOnClickListener {
            Log.i("", "onViewCreated: ${it.id}")
        }

        val userId = UserSession.user?.id ?: 0
        val compras = DataBase.compras.filter { it.usuarioId == userId}

        binding.histCompRVContent.setHasFixedSize(true)
        binding.histCompRVContent.layoutManager = LinearLayoutManager(view.context)
        binding.histCompRVContent.adapter = HistorialCompraAdapter(compras)


    }

}