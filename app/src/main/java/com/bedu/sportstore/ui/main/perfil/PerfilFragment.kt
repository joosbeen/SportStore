package com.bedu.sportstore.ui.main.perfil

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.bedu.sportstore.R
import com.bedu.sportstore.databinding.FragmentPerfilBinding
import com.bedu.sportstore.db.DataBase
import com.bedu.sportstore.model.entity.PerfilEntity
import com.bedu.sportstore.repository.local.AppDatabaseRoom
import com.bedu.sportstore.utileria.PermissionsManager
import com.bedu.sportstore.utileria.UserSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PerfilFragment : Fragment(R.layout.fragment_perfil) {

    private lateinit var bdg: FragmentPerfilBinding
    private val permissionsManager = PermissionsManager()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bdg = FragmentPerfilBinding.bind(view)

        /*UserSession.user?.apply {
            bdg.txtPerfiloutNombre.text = this.nombre
            bdg.txtPerfiloutEmail.text = this.correo
            bdg.txtPerfiloutCompras.text =
                DataBase.compras.filter { it.usuarioId == this.id }.size.toString()
        }*/

        val databaseRoom = AppDatabaseRoom.getDatabase(requireContext())
        val perfilDao = databaseRoom.perfilDao()
        var usuarios = listOf<PerfilEntity>()
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                usuarios = perfilDao.getAll()
            }
            bdg.txtPerfiloutNombre.text = usuarios[0].nombre
            bdg.txtPerfiloutEmail.text = usuarios[0].correo
            bdg.txtPerfiloutCompras.text =
                DataBase.compras.filter { it.usuarioId == usuarios[0].uid }.size.toString()
        }


        bdg.txtPerfilLatitudOut.text = "0.0000"
        bdg.txtPerfilLongitudOut.text = "0.0000"

        bdg.imgLocationUser.setOnClickListener { getLocation()}
    }

    private fun getLocation() {

        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        val isAuthorized = permissionsManager.permissionsAreGranted(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)

        if(isAuthorized) {
            requestLocation()
        } else {
            permissionsManager.requestPermissions(requireActivity(), permissions, PermissionsManager.LOCATION_PERMISSION_REQUEST_CODE)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PermissionsManager.LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestLocation()
                Log.i("sportlocation", "onRequestPermissionsResult: permisto otorgados")
            } else {
                Log.i("sportlocation", "onRequestPermissionsResult: permisto denegado")
                // Permiso denegado, manejar la situación en consecuencia
                // ...
            }
        }

    }

    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        permissionsManager.locationClient(requireActivity())
            .lastLocation
            .addOnSuccessListener { location: Location? ->
                // Obtener las coordenadas de la ubicación
                val latitude = location?.latitude
                val longitude = location?.longitude
                bdg.txtPerfilLatitudOut.text = location?.latitude.toString()
                bdg.txtPerfilLongitudOut.text = location?.longitude.toString()
                Log.i("sportlocation", "requestLocation success: latitude: $latitude, longitude: $longitude")
            }
            .addOnFailureListener { exception: Exception ->
                Log.i("sportlocation", "requestLocation failure: ${exception.message}")
            }
    }
}