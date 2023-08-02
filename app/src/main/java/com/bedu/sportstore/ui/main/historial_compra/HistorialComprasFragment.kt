package com.bedu.sportstore.ui.main.historial_compra

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bedu.sportstore.R
import com.bedu.sportstore.databinding.FragmentHistorialComprasBinding
import com.bedu.sportstore.repository.remote.firebase.CompraReference
import com.bedu.sportstore.ui.main.historial_compra.adapter.HistorialCompraAdapter
import com.bedu.sportstore.ui.toolbar.ToolbarBasic
import com.bedu.sportstore.utileria.Utility
import com.google.firebase.auth.FirebaseAuth

class HistorialComprasFragment : Fragment(R.layout.fragment_historial_compras) {

    private lateinit var binding: FragmentHistorialComprasBinding
    private val fbTblHistorialCompras = CompraReference()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHistorialComprasBinding.bind(view)

        ToolbarBasic().show((activity as AppCompatActivity?)!!, "HISTORIAL DE COMPRA", false)

        binding.histCompRVContent.setHasFixedSize(true)
        binding.histCompRVContent.layoutManager = LinearLayoutManager(requireContext())
        cargarHistorialCompras()

    }

    private fun cargarHistorialCompras() {
        FirebaseAuth.getInstance().currentUser?.let { user ->
            fbTblHistorialCompras.findByUser(user.uid) { documents ->
                if (documents.isSuccessful) {
                    documents.compras?.let {

                        binding.histCompRVContent.visibility =
                            if (it.isEmpty()) View.GONE else View.VISIBLE
                        binding.notContent.visibility =
                            if (it.isEmpty()) View.VISIBLE else View.GONE

                        if (it.isNotEmpty())
                            binding.histCompRVContent.adapter = HistorialCompraAdapter(it)
                    }
                } else {
                    Utility.displaySnackBar(
                        requireView(),
                        getString(R.string.msg_error_cargar_histotial_compras),
                        requireContext()
                    )
                }
            }
        }
    }

}