package com.bedu.sportstore.ui.fragments.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.bedu.sportstore.MainActivity
import com.bedu.sportstore.R
import com.bedu.sportstore.databinding.FragmentSignUpBinding
import com.bedu.sportstore.db.DataBase
import com.bedu.sportstore.db.Usuario
import com.bedu.sportstore.utileria.Form
import com.bedu.sportstore.utileria.UserSession

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var binding: FragmentSignUpBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)
        binding.btnSigninSubmit.setOnClickListener { onClickSubmitRegister() }
    }

    private fun onClickSubmitRegister() {

        val nombre = Form.getTextFromEdit(binding.edtSigninNombre)
        val email = Form.getTextFromEdit(binding.edtSigninEmail)
        val contrasena = Form.getTextFromEdit(binding.edtSigninContrasena)
        val contrasenaConfirmar = Form.getTextFromEdit(binding.edtSigninContrasenaConfirmar)
        val contrasena2 = contrasena.replace(" ", "")

        // Validar que los campos no esten vacios
        if (Form.isInvalidText(nombre) || Form.isInvalidText(email) ||
            Form.isInvalidText(contrasena) || Form.isInvalidText(contrasenaConfirmar)) {
            Toast.makeText(activity, R.string.error_msj_form_incompleto, Toast.LENGTH_SHORT).show()
            return
        }

        // Validar nombre minimo 3 caracteres
        if(nombre.length<3) {
            Toast.makeText(activity, R.string.error_msj_nombre_min_caract, Toast.LENGTH_SHORT).show()
            return
        }

        // Validar que contraseñas no contengan espacios
        if (contrasena!=contrasena2){
            Toast.makeText(activity, R.string.error_msj_contrasena_esp, Toast.LENGTH_SHORT).show()
            return
        }

        // Validar contraseña minimo 5 caracteres
        if(contrasena.length<5) {
            Toast.makeText(activity, R.string.error_msj_contrasena_min_caract, Toast.LENGTH_SHORT).show()
            return
        }

        // Validar que el correo tenga el formato correcto
        if (!Form.validarEmail(email)){
            Toast.makeText(activity, R.string.error_msj_correo_fort, Toast.LENGTH_SHORT).show()
            return
        }

        // validar si el correo ya se encuentra registrado
        val existeUsuario = DataBase.usuarios.find { it.correo.lowercase()==email.lowercase() }
        if (existeUsuario!=null){
            Toast.makeText(activity, R.string.error_msj_correo_registrado, Toast.LENGTH_SHORT).show()
            return
        }

        // Validar si las contraseña no coinciden
        if (contrasena != contrasenaConfirmar){
            Toast.makeText(activity, R.string.error_msj_contrasenas_diferentes, Toast.LENGTH_SHORT).show()
            return
        }

        // Guargar el usuario en sesion
        val id = (DataBase.usuarios.size + 1L)
        val usuario = Usuario(id, nombre, email, contrasena, "cliente")
        UserSession.user = usuario

        // Cambiar al ActivityMain
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.putExtra("usuario", usuario.toString())
        startActivity(intent)
        activity?.finish()
    }

}