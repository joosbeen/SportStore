package com.bedu.sportstore.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bedu.sportstore.R
import com.bedu.sportstore.databinding.FragmentAuthHomeBinding
import com.bedu.sportstore.utileria.Utility
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn.getClient
import com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthHomeFragment : Fragment(R.layout.fragment_auth_home) {

    private lateinit var binding: FragmentAuthHomeBinding
    private val RC_SIGN_IN = 692
    private var showOneTapUI = true

    // signin google
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthHomeBinding.bind(view)

        binding.authHomeBtnSignin.setOnClickListener { onClickChangeFragment(it.id) }
        binding.authHomeBtnSignup.setOnClickListener { onClickChangeFragment(it.id) }
        onClickBtnSignipGoogle()

        // signin google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = getClient(requireActivity(), gso)
        auth = Firebase.auth
    }

    private fun onClickBtnSignipGoogle() {
        binding.btnSigninGoogle.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    private fun onClickChangeFragment(id: Int) {
        val action =
            if (id == binding.authHomeBtnSignin.id) R.id.action_authHomeFragment_to_signInFragment
            else R.id.action_authHomeFragment_to_signUpFragment
        findNavController().navigate(action)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG_NAME, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG_NAME, "Google sign in failed", e)
                Utility.displaySnackBar(binding.root, "Google sign in failed", requireContext(), R.color.red)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG_NAME, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user, null)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG_NAME, "signInWithCredential:failure", task.exception)
                    updateUI(null, task.exception)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?, exception: Exception?) {
        if (exception != null) {
            visibilityButtons(true)
            Utility.displaySnackBar(binding.root, exception.message.toString(), requireContext(), R.color.red)
        } else {
            visibilityButtons(true)
            Utility.displaySnackBar(binding.root, "Login was successful: ", requireContext(), R.color.green)
        }
    }

    private fun visibilityButtons(visibility: Boolean) {
        val isShowBtns = if(visibility) View.VISIBLE else View.GONE
        val isShowProgress = if(!visibility) View.VISIBLE else View.GONE
        binding.btnSigninGoogle.visibility = isShowBtns
        binding.authHomeBtnSignup.visibility = isShowBtns
        binding.authHomeBtnSignin.visibility = isShowBtns
        binding.loadingProgressBar.visibility = isShowProgress
    }

    companion object {
        const val TAG_NAME = "authtaghome"
    }
}