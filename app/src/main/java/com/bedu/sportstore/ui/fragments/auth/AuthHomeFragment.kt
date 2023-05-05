package com.bedu.sportstore.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.bedu.sportstore.R


class AuthHomeFragment : Fragment() {

    private lateinit var auth_home_btn_signin: Button
    private lateinit var auth_home_btn_signup: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_auth_home, container, false)

        auth_home_btn_signin = view.findViewById(R.id.auth_home_btn_signin)
        auth_home_btn_signup = view.findViewById(R.id.auth_home_btn_signup)

        auth_home_btn_signin.setOnClickListener { onClickChangeFragment(it.id) }
        auth_home_btn_signup.setOnClickListener { onClickChangeFragment(it.id) }

        return view
    }

    private fun onClickChangeFragment(id: Int) {

        val fragment = if (id == auth_home_btn_signin.id) SignInFragment() else SignUpFragment()

        requireActivity().supportFragmentManager.commit {
            replace(R.id.authFragmentContainer, fragment)
            addToBackStack("authHomeFragment")
        }
    }

}