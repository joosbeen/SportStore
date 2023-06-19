package com.bedu.sportstore.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.navigation.fragment.findNavController
import com.bedu.sportstore.R
import com.bedu.sportstore.databinding.FragmentAuthHomeBinding


class AuthHomeFragment : Fragment(R.layout.fragment_auth_home) {

    private lateinit var binding: FragmentAuthHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthHomeBinding.bind(view)

        binding.authHomeBtnSignin.setOnClickListener { onClickChangeFragment(it.id) }
        binding.authHomeBtnSignup.setOnClickListener { onClickChangeFragment(it.id) }

    }

    private fun onClickChangeFragment(id: Int) {

        val action =
            if (id == binding.authHomeBtnSignin.id) R.id.action_authHomeFragment_to_signInFragment
            else R.id.action_authHomeFragment_to_signUpFragment
        findNavController().navigate(action)

    }

}