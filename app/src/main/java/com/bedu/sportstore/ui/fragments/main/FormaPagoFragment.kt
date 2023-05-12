package com.bedu.sportstore.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bedu.sportstore.R
import com.bedu.sportstore.databinding.FragmentFormaPagoBinding
import com.bedu.sportstore.utileria.Form

class FormaPagoFragment : Fragment(R.layout.fragment_forma_pago) {

    private lateinit var binding: FragmentFormaPagoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFormaPagoBinding.bind(view)
        binding.fpBtnPagar.setOnClickListener { validarDatosTarjeta() }
    }

    private fun validarDatosTarjeta() {

        val context = activity?.applicationContext

        if (Form.isInvalidText(binding.pagoEdtNumeroTargeta.toString())) {
            Toast.makeText(context, "Ingrese un ${R.string.hint_numero_tarjeta} válido", Toast.LENGTH_SHORT).show()
        } else if (Form.isInvalidText(binding.pagoEdtExpiracion.toString())) {
            Toast.makeText(context, "Ingrese un ${R.string.himt_expiracion} válido", Toast.LENGTH_SHORT).show()
        } else if (Form.isInvalidText(binding.pagoEdtCVC.toString())) {
            Toast.makeText(context, "Ingrese un ${R.string.hint_cvc} válido", Toast.LENGTH_SHORT).show()
        } else if (Form.isInvalidText(binding.pagoEdtNombreTitular.toString())) {
            Toast.makeText(context, "Ingrese un ${R.string.hint_nombre_titular} válido", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Se datos de tarjeta valida", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}