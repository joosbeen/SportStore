package com.bedu.sportstore.ui.fragments.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bedu.sportstore.MainActivity
import com.bedu.sportstore.R
import com.bedu.sportstore.databinding.FragmentFormaPagoBinding
import com.bedu.sportstore.db.DataBase
import com.bedu.sportstore.db.Usuario
import com.bedu.sportstore.utileria.Form
import com.bedu.sportstore.utileria.UserSession

class FormaPagoFragment : Fragment(R.layout.fragment_forma_pago) {

    private lateinit var binding: FragmentFormaPagoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFormaPagoBinding.bind(view)
        binding.fpBtnPagar.setOnClickListener { validarDatosTarjeta() }
    }

    private fun validarDatosTarjeta() {

        val context = activity?.applicationContext

        val etaNumero = Form.getTextFromEdit(binding.pagoEdtNumeroTargeta)
        val etaNumero2 = etaNumero.replace(" ", "")
        val etaExpiracion = Form.getTextFromEdit(binding.pagoEdtExpiracion)
        val etaCVC = Form.getTextFromEdit(binding.pagoEdtCVC)
        val etaTitular = Form.getTextFromEdit(binding.pagoEdtNombreTitular)

        // Validar si no es vacio num eta
        if (Form.isInvalidText(etaNumero)) {
            Toast.makeText(context, R.string.error_msj_eta_numero_requerido, Toast.LENGTH_SHORT).show()
            return
        }

        // Validar 16 digitos
        if (etaNumero2.length!=16) {
            Toast.makeText(context, R.string.error_msj_eta_numero_invalido, Toast.LENGTH_SHORT).show()
            return
        }

        // Validar si no es vacio expiracion
         if (Form.isInvalidText(etaExpiracion)) {
            Toast.makeText(context, R.string.error_msj_eta_expiracion_invalido, Toast.LENGTH_SHORT).show()
        }

        // Validar CVC vacio
        if (Form.isInvalidText(etaCVC)) {
            Toast.makeText(context, R.string.error_msj_eta_cvc_requerida, Toast.LENGTH_SHORT).show()
        }

        // Validar CVC tamaño
        if (etaCVC.length!=3) {
            Toast.makeText(context, R.string.error_msj_eta_cvc_invalido, Toast.LENGTH_SHORT).show()
            return
        }

        if (Form.isInvalidText(etaTitular)) {
            Toast.makeText(context, R.string.error_msj_eta_titular_requerida, Toast.LENGTH_SHORT).show()
            return
        }

        Toast.makeText(context, "Los datos de tarjeta son válidos, parte en desarrollo pendiente.", Toast.LENGTH_SHORT).show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}