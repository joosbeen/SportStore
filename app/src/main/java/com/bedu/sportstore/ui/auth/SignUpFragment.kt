package com.bedu.sportstore.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bedu.sportstore.R
import com.bedu.sportstore.databinding.FragmentSignUpBinding
import com.bedu.sportstore.model.Usuario
import com.bedu.sportstore.model.collections.User
import com.bedu.sportstore.model.entity.PerfilEntity
import com.bedu.sportstore.repository.local.AppDatabaseRoom
import com.bedu.sportstore.ui.MainActivity
import com.bedu.sportstore.utileria.Form
import com.bedu.sportstore.utileria.UserSession
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)

        auth = Firebase.auth
        db = Firebase.firestore

        binding.btnSigninSubmit.setOnClickListener { onClickSubmitRegister() }

        binding.edtSigninNombre.setText("JOSE BENITO")
        binding.edtSigninEmail.setText("carlos@gmail.com")
        binding.edtSigninContrasena.setText("qwerty")
        binding.edtSigninContrasenaConfirmar.setText("qwerty")
    }

    private fun onClickSubmitRegister() {

        val nombre = Form.getTextFromEdit(binding.edtSigninNombre)
        val email = Form.getTextFromEdit(binding.edtSigninEmail)
        val contrasena = Form.getTextFromEdit(binding.edtSigninContrasena)
        val contrasenaConfirmar = Form.getTextFromEdit(binding.edtSigninContrasenaConfirmar)
        val contrasena2 = contrasena.replace(" ", "")

        // Validar que los campos no esten vacios
        if (Form.isInvalidText(nombre) || Form.isInvalidText(email) ||
            Form.isInvalidText(contrasena) || Form.isInvalidText(contrasenaConfirmar)
        ) {
            Toast.makeText(activity, R.string.error_msj_form_incompleto, Toast.LENGTH_SHORT).show()
            return
        }

        // Validar nombre minimo 3 caracteres
        if (nombre.length < 3) {
            Toast.makeText(activity, R.string.error_msj_nombre_min_caract, Toast.LENGTH_SHORT)
                .show()
            return
        }

        // Validar que contraseñas no contengan espacios
        if (contrasena != contrasena2) {
            Toast.makeText(activity, R.string.error_msj_contrasena_esp, Toast.LENGTH_SHORT).show()
            return
        }

        // Validar contraseña minimo 5 caracteres
        if (contrasena.length < 5) {
            Toast.makeText(activity, R.string.error_msj_contrasena_min_caract, Toast.LENGTH_SHORT)
                .show()
            return
        }

        // Validar que el correo tenga el formato correcto
        if (!Form.validarEmail(email)) {
            Toast.makeText(activity, R.string.error_msj_correo_fort, Toast.LENGTH_SHORT).show()
            return
        }

        // Validar si las contraseña no coinciden
        if (contrasena != contrasenaConfirmar) {
            Toast.makeText(activity, R.string.error_msj_contrasenas_diferentes, Toast.LENGTH_SHORT)
                .show()
            return
        }

        createAccount(email, contrasena, nombre)

    }

    private fun createAccount(email: String, password: String, nombre: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        saveUser(user.uid, email, nombre)
                    }
                } else {
                    task.exception?.let { e ->
                        e.message?.let { showSnackbar(it) }
                    }
                }
            }

    }

    private fun saveUser(uid: String, email: String, nombre: String) {

        val databaseRoom = AppDatabaseRoom.getDatabase(requireActivity())
        val perfilDao = databaseRoom.perfilDao()
        val usuario = User(uid, email, nombre)

        db.collection("usuarios")
            .add(usuario)
            .addOnSuccessListener { documentReference ->
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        perfilDao.insert(
                            PerfilEntity(uid.length.toLong(), nombre, email, "cliente", "")
                        )
                    }
                }
                val documentId = documentReference.id
                UserSession.user = Usuario(uid.length.toLong(), nombre, email, "", "cliente")
                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.putExtra("usuario", UserSession.user.toString())
                startActivity(intent)
                activity?.finish()

            }
            .addOnFailureListener { e ->
                // Ocurrió un error al guardar el documento
                Log.e(TAG, "saveUser: failure")
            }
    }

    private fun showSnackbar(msg: String): Unit {
        val snack = Snackbar.make(requireView(), msg, Snackbar.LENGTH_LONG)
        snack.show()
    }

    companion object {
        const val TAG = "sportsignup"
    }
}