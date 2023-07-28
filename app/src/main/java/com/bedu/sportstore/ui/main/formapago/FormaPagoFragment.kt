package com.bedu.sportstore.ui.main.formapago

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bedu.sportstore.R
import com.bedu.sportstore.databinding.FragmentFormaPagoBinding
import com.bedu.sportstore.model.entity.CarritoEntity
import com.bedu.sportstore.repository.local.AppDatabaseRoom
import com.bedu.sportstore.repository.remote.firebase.CompraCollection
import com.bedu.sportstore.repository.remote.firebase.CompraDetalle
import com.bedu.sportstore.repository.remote.firebase.CompraDetalleReference
import com.bedu.sportstore.repository.remote.firebase.CompraReference
import com.bedu.sportstore.utileria.Form
import com.bedu.sportstore.utileria.Utility
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class FormaPagoFragment : Fragment(R.layout.fragment_forma_pago) {

    private lateinit var binding: FragmentFormaPagoBinding
    private var costoSubtotal = 0f
    private var costoEnvio = 50f
    private var carrito: List<CarritoEntity> = listOf()
    private val carritoDao by lazy { AppDatabaseRoom.getDatabase(requireContext()).carritoDao() }
    private val fbTblCompra = CompraReference()
    private val fbTblComprasDetalle = CompraDetalleReference()
    private lateinit var itemHistorialComprasFragment: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFormaPagoBinding.bind(view)

        binding.toolBarFragment.title = getString(R.string.title_detalles_del_pago)
        binding.toolBarFragment.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.toolBarFragment.setNavigationOnClickListener {
            if (it.id == -1)
                findNavController().navigate(R.id.action_formaPagoFragment_to_carritoFragment)
        }

        binding.fpBtnPagar.setOnClickListener { validarDatosTarjeta() }

        calcularSubtotal()

        binding.fpCostoEnvio.text = "$ ${costoEnvio} MXN"

        val bottomNavigationView =
            activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        itemHistorialComprasFragment =
            bottomNavigationView?.findViewById(R.id.historialComprasFragment)!!


    }

    private fun calcularSubtotal() {

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                carrito = carritoDao.getAll()
                carrito.forEach {
                    costoSubtotal += it.precio
                }
                binding.fpCostoSubtotal.text = "$ ${costoSubtotal} MXN"
                binding.fpCostoTotal.text = "$${costoSubtotal + costoEnvio} MXN"
                binding.fpBtnPagar.text = "PAGAR $${costoSubtotal + costoEnvio} MXN"
            }
        }

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
            showMessage(R.string.error_msj_eta_numero_requerido)
            return
        }

        // Validar 16 digitos
        if (etaNumero2.length != 16) {
            showMessage(R.string.error_msj_eta_numero_invalido)
            return
        }

        // Validar si no es vacio expiracion
        if (Form.isInvalidText(etaExpiracion)) {
            showMessage(R.string.error_msj_eta_expiracion_invalido)
            return
        }

        // Validar CVC vacio
        if (Form.isInvalidText(etaCVC)) {
            showMessage(R.string.error_msj_eta_cvc_requerida)
            return
        }

        // Validar CVC tamaño
        if (etaCVC.length != 3) {
            showMessage(R.string.error_msj_eta_cvc_invalido)
            return
        }

        if (Form.isInvalidText(etaTitular)) {
            showMessage(R.string.error_msj_eta_titular_requerida)
            return
        }

        guardarCompra()

    }

    private fun guardarCompra() {

        FirebaseAuth.getInstance().currentUser?.let { user ->
            fbTblCompra.create(
                CompraCollection(
                    folio = Date().time,
                    usuario = user.uid,
                    subtotal = costoSubtotal,
                    envio = costoEnvio,
                    productos = carrito,
                    estado = "Pendiente"
                )
            ) { resource ->
                if (resource.isSuccessful) {
                    resource.documentReference?.id?.let { idDocument ->
                        //guardarProductosFirebase(idDocument)
                        limpiarCarrito()
                    }
                } else {
                    Utility.displaySnackBar(
                        requireView(),
                        getString(R.string.msg_error_register_compra),
                        requireContext(),
                        R.color.red
                    )
                }
            }

        }

    }

    private fun guardarProductosFirebase(uid: String) {

        val compraDetalle = CompraDetalle(compra = uid, productos = carrito)

        fbTblComprasDetalle.create(compraDetalle) { resource ->
            if (resource.isSuccessful) {
                limpiarCarrito()
            } else {
                Utility.displaySnackBar(
                    requireView(),
                    getString(R.string.msg_error_register_compra),
                    requireContext(),
                    R.color.red
                )
            }
        }

    }

    private fun limpiarCarrito() {
        lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                carrito.forEach {
                    carritoDao.delete(it)
                }
                itemHistorialComprasFragment?.performClick()
            }
        }
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