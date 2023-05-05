package com.bedu.sportstore.ui.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.commit
import com.bedu.sportstore.R

class SignInFragment : Fragment() {

    private lateinit var edtSigninEmail: EditText
    private lateinit var edtSigninContrasena: EditText
    private lateinit var btnSigninSubmit: Button
    private lateinit var txtSigninRegistrarse: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)

        // variable initialization
        edtSigninEmail = view.findViewById(R.id.edtSigninEmail)
        edtSigninContrasena = view.findViewById(R.id.edtSigninContrasena)
        btnSigninSubmit = view.findViewById(R.id.btnSigninSubmit)
        txtSigninRegistrarse = view.findViewById(R.id.txtSigninRegistrarse)

        // click events
        btnSigninSubmit.setOnClickListener { onClickSigninSubmit() }
        txtSigninRegistrarse.setOnClickListener { onClickSigninRegister() }



        return view
    }

    private fun onClickSigninRegister() {
        //Toast.makeText(getActivity(), "click register option", Toast.LENGTH_SHORT).show()
        requireActivity().supportFragmentManager.commit {
            replace(R.id.authFragmentContainer, SignUpFragment())
            addToBackStack("authHomeFragment")
        }
    }

    private fun onClickSigninSubmit() {
        Toast.makeText(getActivity(), "click submit button", Toast.LENGTH_SHORT).show()
    }

}