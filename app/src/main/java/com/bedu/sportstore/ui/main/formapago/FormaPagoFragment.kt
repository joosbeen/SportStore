package com.bedu.sportstore.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.bedu.sportstore.ui.main.home.HomeFragment
import com.bedu.sportstore.R
import com.bedu.sportstore.databinding.FragmentFormaPagoBinding
import com.bedu.sportstore.db.CarritoProducto
import com.bedu.sportstore.db.Compra
import com.bedu.sportstore.db.DataBase
import com.bedu.sportstore.utileria.Form
import com.bedu.sportstore.utileria.UserSession
import com.bedu.sportstore.utileria.UtilFragment
import com.google.android.material.snackbar.Snackbar
import java.util.Date

class FormaPagoFragment : Fragment(R.layout.fragment_forma_pago) {

    private lateinit var binding: FragmentFormaPagoBinding
    private var costoSubtotal = 0f
    private var costoEnvio = 50f

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFormaPagoBinding.bind(view)
        binding.toolBarFragment.title = getString(R.string.title_detalles_del_pago)
        binding.toolBarFragment.setNavigationIcon(R.drawable.ic_arrow_back) // need to set the icon here to have a navigation icon. You can simple create an vector image by "Vector Asset" and using here
        binding.toolBarFragment.setNavigationOnClickListener {

            if (it.id == -1) UtilFragment().replaceFragmetnMain(
                requireActivity().supportFragmentManager,
                CarritoFragment()
            )
        }

        binding.fpBtnPagar.setOnClickListener { validarDatosTarjeta() }

        val carrito = DataBase.carrito.filter { it.usuarioId == UserSession.user?.id }
        carrito.forEach {
            val prod = DataBase.productos.find { p -> p.id == it.productoId }
            costoSubtotal += prod?.precio ?: 0f
        }

        binding.fpCostoSubtotal.text = "$ ${costoSubtotal} MXN"
        binding.fpCostoEnvio.text = "$ ${costoEnvio} MXN"
        binding.fpCostoTotal.text = "$${costoSubtotal + costoEnvio} MXN"
        binding.fpBtnPagar.text = "PAGAR $${costoSubtotal + costoEnvio} MXN"


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
            showMessage( R.string.error_msj_eta_numero_requerido)
            return
        }

        // Validar 16 digitos
        if (etaNumero2.length != 16) {
            showMessage( R.string.error_msj_eta_numero_invalido)
            return
        }

        // Validar si no es vacio expiracion
        if (Form.isInvalidText(etaExpiracion)) {
            showMessage( R.string.error_msj_eta_expiracion_invalido)
            return
        }

        // Validar CVC vacio
        if (Form.isInvalidText(etaCVC)) {
            showMessage( R.string.error_msj_eta_cvc_requerida)
            return
        }

        // Validar CVC tamaño
        if (etaCVC.length != 3) {
            showMessage( R.string.error_msj_eta_cvc_invalido)
            return
        }

        if (Form.isInvalidText(etaTitular)) {
            showMessage( R.string.error_msj_eta_titular_requerida)
            return
        }

        guardarCompra()

    }

    private fun guardarCompra() {

        // Crear nueva compra
        val date = Date()
        val compra = Compra(
            "${date.time}",
            "${date.date}/${date.month}/${date.year}",
            "Entregado",
            (costoSubtotal + costoEnvio),
            UserSession.user?.id ?: 0
        )
        DataBase.compras.add(compra)

        // Limpiar Carrito
        val  carrito = DataBase.carrito.filter { it.usuarioId != UserSession.user?.id }
        DataBase.carrito = carrito as MutableList<CarritoProducto>

        showMessage(R.string.compra_exitosa)

        // Ir al fragment home
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.frame_Layout, HomeFragment())
        fragmentTransaction?.commit()

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun showMessage(msg: Int) {
        view?.let {
            Snackbar.make(
                it,
                msg, Snackbar.LENGTH_SHORT
            ).show()
        }
    }

}