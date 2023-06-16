package com.bedu.sportstore.ui.fragments.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bedu.sportstore.MainActivity
import com.bedu.sportstore.R
import com.bedu.sportstore.databinding.FragmentSignInBinding
import com.bedu.sportstore.db.DataBase
import com.bedu.sportstore.utileria.Form
import com.bedu.sportstore.utileria.UserSession

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private lateinit var bdg: FragmentSignInBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bdg = FragmentSignInBinding.bind(view)

        bdg.btnSigninSubmit.setOnClickListener { onClickSigninSubmit() }
        bdg.txtSigninRegistrarse.setOnClickListener { onClickSigninRegister() }
    }

    private fun onClickSigninRegister() {
        findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
    }

    private fun onClickSigninSubmit() {

        val correo = Form.getTextFromEdit(bdg.edtSigninEmail)
        val contrasena = Form.getTextFromEdit(bdg.edtSigninContrasena)
        val password: String = contrasena.replace(" ", "")

        // Validar que no esten vacios los campos
        if (Form.isInvalidText(correo) || Form.isInvalidText(contrasena)) {
            Toast.makeText(activity, R.string.error_msj_correo_contrasena_req, Toast.LENGTH_SHORT).show()
            return
        }

        // Validar que la contraseñas no contengan espacios
        if (contrasena!=password){
            Toast.makeText(activity, R.string.error_msj_contrasena_esp, Toast.LENGTH_SHORT).show()
            return
        }

        // Validar contraseña minimo 5 caracteres
        if(contrasena.length<5) {
            Toast.makeText(activity, R.string.error_msj_contrasena_min_caract, Toast.LENGTH_SHORT).show()
            return
        }

        // Validar que el correo tenga el formato correcto
        if (!Form.validarEmail(correo)){
            Toast.makeText(activity, R.string.error_msj_correo_fort, Toast.LENGTH_SHORT).show()
            return
        }

        // Buscar usuario con el correo y contraseña
        val usuario = DataBase.usuarios.find {
            it.correo.lowercase() == correo.lowercase() && it.contrasena.lowercase() == contrasena.lowercase()
        }

        // Validar si no se encontro algun usuario
        if (usuario == null) {
            Toast.makeText(activity, R.string.error_msj_correo_contrasena_inv, Toast.LENGTH_SHORT).show()
            return
        }

        // Guardar datos del usuario
        UserSession.user = usuario

        // Cambiar de activity al MainActivity
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.putExtra("usuario", usuario.toString())
        startActivity(intent)
        activity?.finish()
    }

}
