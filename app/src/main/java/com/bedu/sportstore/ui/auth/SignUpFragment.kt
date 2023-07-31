package com.bedu.sportstore.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bedu.sportstore.ui.MainActivity
import com.bedu.sportstore.R
import com.bedu.sportstore.databinding.FragmentSignUpBinding
import com.bedu.sportstore.db.Usuario
import com.bedu.sportstore.model.entity.PerfilEntity
import com.bedu.sportstore.model.request.SignupVO
import com.bedu.sportstore.model.response.AuthResponse
import com.bedu.sportstore.repository.local.AppDatabaseRoom
import com.bedu.sportstore.repository.remote.SportStoreHttp
import com.bedu.sportstore.utileria.Form
import com.bedu.sportstore.utileria.UserSession
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var binding: FragmentSignUpBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)
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

        val auth = SportStoreHttp.authHttp()
        val signup = SignupVO(nombre, email, contrasena, contrasenaConfirmar, "cliente")
        Log.i("sportauth", "onClickSubmitRegister: $signup")
        val call = auth.signup(signup)

        call.enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse?>, response: Response<AuthResponse?>) {

                if (response.body() != null && response.body()?.success == true && response.body()?.usuario != null) {

                    val databaseRoom = AppDatabaseRoom.getDatabase(requireActivity())
                    val perfilDao = databaseRoom.perfilDao()
                    response.body()?.usuario?.let {
                        lifecycleScope.launch {
                            withContext(Dispatchers.IO) {
                                perfilDao.insert(
                                    PerfilEntity(it.id, it.nombre, it.correo, it.rol, "")
                                )
                            }
                        }

                        UserSession.user = Usuario(it.id, it.nombre, it.correo, "", it.rol)
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        intent.putExtra("usuario", response.body()?.usuario.toString())
                        startActivity(intent)
                        activity?.finish()
                    }

                } else {
                    val msg = response.body()?.message ?: "No se pudo registrar intente mas tarde!"
                    showSnackbar(msg)
                }

            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                showSnackbar(getString(R.string.msg_error_server))
            }
        })

        // Guargar el usuario en sesion
        /*val id = (DataBase.usuarios.size + 1L)
        val usuario = Usuario(id, nombre, email, contrasena, "cliente")
        UserSession.user = usuario*/

        // Cambiar al ActivityMain
        /*val intent = Intent(requireContext(), MainActivity::class.java)
        intent.putExtra("usuario", usuario.toString())
        startActivity(intent)
        activity?.finish()*/
    }

    private fun showSnackbar(msg: String): Unit {
        val snack = Snackbar.make(requireView(), msg, Snackbar.LENGTH_LONG)
        snack.show()
    }
}