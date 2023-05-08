package com.bedu.sportstore.ui.fragments.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.bedu.sportstore.MainActivity
import com.bedu.sportstore.R
import com.bedu.sportstore.db.DataBase
import com.bedu.sportstore.db.Usuario
import com.bedu.sportstore.utileria.Form

class SignUpFragment : Fragment() {


    private lateinit var edtSigninNombre: EditText
    private lateinit var edtSigninEmail: EditText
    private lateinit var edtSigninContrasena: EditText
    private lateinit var edtSigninContrasenaConfirmar: EditText
    private lateinit var btnSigninSubmit: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        //
        edtSigninNombre = view.findViewById(R.id.edtSigninNombre)
        edtSigninEmail = view.findViewById(R.id.edtSigninEmail)
        edtSigninContrasena = view.findViewById(R.id.edtSigninContrasena)
        edtSigninContrasenaConfirmar = view.findViewById(R.id.edtSigninContrasenaConfirmar)
        btnSigninSubmit = view.findViewById(R.id.btnSigninSubmit)

        //
        btnSigninSubmit.setOnClickListener { onClickSubmitRegister() }

        return view
    }

    private fun onClickSubmitRegister() {

        val nombre = edtSigninNombre.text.toString()
        val email = edtSigninEmail.text.toString()
        val contrasena = edtSigninContrasena.text.toString()
        val contrasenaConfirmar = edtSigninContrasenaConfirmar.text.toString()

        if (Form.isInvalidText(nombre) || Form.isInvalidText(email) ||
            Form.isInvalidText(contrasena) || Form.isInvalidText(contrasenaConfirmar)) {
            Toast.makeText(activity, "Debe llenar correctamente todos los campos!", Toast.LENGTH_SHORT).show()
            return
        }

        val existeUsuario = DataBase.usuarios.find { it.correo.lowercase()==email.lowercase() }

        if (existeUsuario!=null){
            Toast.makeText(activity, "El correo ya se encuentra registrado, ingrese otro!", Toast.LENGTH_SHORT).show()
            return
        }

        if (contrasena != contrasenaConfirmar){
            Toast.makeText(activity, "Las contraseñas no coiciden!", Toast.LENGTH_SHORT).show()
            return
        }

        val id = (DataBase.usuarios.size + 1L)
        val usuario = Usuario(id, nombre, email, contrasena, "cliente")

        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.putExtra("usuario", usuario.toString())
        startActivity(intent)
        activity?.finish()
    }

}