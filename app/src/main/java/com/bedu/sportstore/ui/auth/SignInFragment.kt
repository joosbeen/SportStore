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
import com.bedu.sportstore.repository.local.AppDatabaseRoom
import com.bedu.sportstore.ui.MainActivity
import com.bedu.sportstore.utileria.Form
import com.bedu.sportstore.utileria.UserSession
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private lateinit var bdg: FragmentSignInBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bdg = FragmentSignInBinding.bind(view)

        auth = Firebase.auth
        db = Firebase.firestore

        bdg.edtSigninEmail.setText("carlos@gmail.com")
        bdg.edtSigninContrasena.setText("qwerty")

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
        //val auth = SportStoreHttp.authHttp()
        //val call = auth.signin(login)

        auth.signInWithEmailAndPassword(correo, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    user?.let {
                        createSession(user.uid, "", correo)
                        searchUserbuUid(user.uid)
                    }
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    task.exception?.let { e ->
                        e.message?.let { showSnackbar(it) }
                    }
                }
            }

    }

    private fun searchUserbuUid(uid: String) {

        val collectionName = "usuarios"
        val fieldName = "userUid"
        val fieldValue = uid

        val query: Query = db.collection(collectionName).whereEqualTo(fieldName, fieldValue)

        query.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document in task.result) {
                    val data = document.data
                    Log.i(TAG, "searchUserbuUid: Login success: $data")
                }
            } else {
                val exception = task.exception
                Log.w(TAG, "searchUserbuUid: exeption: ${exception?.message}", )
            }
        }

    }

    private fun createSession(uid: String, nombre: String, email: String) {

        val databaseRoom = AppDatabaseRoom.getDatabase(requireActivity())
        val perfilDao = databaseRoom.perfilDao()

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                perfilDao.insert(
                    PerfilEntity(uid.length.toLong(), nombre, email, "cliente", "")
                )
            }
        }
        //val documentId = documentReference.id
        UserSession.user = Usuario(uid.length.toLong(), nombre, email, "", "cliente")
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.putExtra("usuario", UserSession.user.toString())
        startActivity(intent)
        activity?.finish()
    }

    private fun showSnackbar(msg: String): Unit {
        val snack = Snackbar.make(requireView(), msg, Snackbar.LENGTH_LONG)
        snack.show()
    }

    companion object {
        const val TAG = "sportsignin"
    }

}
