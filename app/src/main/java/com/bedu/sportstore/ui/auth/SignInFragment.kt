package com.bedu.sportstore.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bedu.sportstore.R
import com.bedu.sportstore.databinding.FragmentSignInBinding
import com.bedu.sportstore.db.Usuario
import com.bedu.sportstore.model.entity.PerfilEntity
import com.bedu.sportstore.model.request.SigninVO
import com.bedu.sportstore.repository.remote.SportStoreHttp
import com.bedu.sportstore.model.response.AuthResponse
import com.bedu.sportstore.repository.local.AppDatabaseRoom
import com.bedu.sportstore.ui.MainActivity
import com.bedu.sportstore.ui.SportApplication
import com.bedu.sportstore.utileria.Form
import com.bedu.sportstore.utileria.TAGS
import com.bedu.sportstore.utileria.UserSession
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private lateinit var bdg: FragmentSignInBinding
    //private val perfilDao by lazy { (requireActivity().application as SportApplication).perfilDao }
    //private val databaseRoom = AppDatabaseRoom.getDatabase(requireActivity())
    //private val perfilDao = databaseRoom.perfilDao()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bdg = FragmentSignInBinding.bind(view)

        bdg.edtSigninEmail.setText("carlos@gmail.com")
        bdg.edtSigninContrasena.setText("Carlos12")

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
            Toast.makeText(activity, R.string.error_msj_correo_contrasena_req, Toast.LENGTH_SHORT)
                .show()
            return
        }

        // Validar que la contraseñas no contengan espacios
        if (contrasena != password) {
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
        if (!Form.validarEmail(correo)) {
            Toast.makeText(activity, R.string.error_msj_correo_fort, Toast.LENGTH_SHORT).show()
            return
        }

        val login = SigninVO(correo, contrasena)
        val auth = SportStoreHttp.authHttp()
        val call = auth.signin(login)



        call.enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {

                if (response.isSuccessful) {
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
                    showSnackbar(getString(R.string.msg_error_login_invalid))
                    Log.e(TAGS.ERROR, "onResponse: ${getString(R.string.msg_error_login_invalid)}")
                }

            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                showSnackbar(getString(R.string.msg_error_server))
                Log.e(TAGS.ERROR, "onResponse: ${getString(R.string.msg_error_server)}")
            }
        })

    }

    private fun showSnackbar(msg: String): Unit {
        val snack = Snackbar.make(requireView(), msg, Snackbar.LENGTH_LONG)
        snack.show()
    }

}
