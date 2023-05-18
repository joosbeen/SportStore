package com.bedu.sportstore.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bedu.sportstore.R
import com.bedu.sportstore.databinding.FragmentPerfilBinding
import com.bedu.sportstore.db.DataBase
import com.bedu.sportstore.utileria.UserSession

class PerfilFragment : Fragment(R.layout.fragment_perfil) {

    private lateinit var bdg: FragmentPerfilBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bdg = FragmentPerfilBinding.bind(view)

        UserSession.user?.apply {
            bdg.txtPerfiloutNombre.text = this.nombre
            bdg.txtPerfiloutEmail.text = this.correo
            bdg.txtPerfiloutCompras.text =
                DataBase.compras.filter { it.usuarioId == this.id }.size.toString()
        }
    }

}