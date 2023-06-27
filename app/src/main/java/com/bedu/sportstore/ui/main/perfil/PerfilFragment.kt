package com.bedu.sportstore.ui.main.perfil

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.lifecycleScope
import com.bedu.sportstore.R
import com.bedu.sportstore.databinding.FragmentPerfilBinding
import com.bedu.sportstore.db.DataBase
import com.bedu.sportstore.model.entity.PerfilEntity
import com.bedu.sportstore.repository.local.AppDatabaseRoom
import com.bedu.sportstore.ui.AuthActivity
import com.bedu.sportstore.ui.MainActivity
import com.bedu.sportstore.utileria.ImageFormat
import com.bedu.sportstore.utileria.PermissionsManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PerfilFragment : Fragment(R.layout.fragment_perfil), PopupMenu.OnMenuItemClickListener {

    private lateinit var bdg: FragmentPerfilBinding
    private val permissionsManager = PermissionsManager()
    private var usuarios = listOf<PerfilEntity>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bdg = FragmentPerfilBinding.bind(view)

        setProfileData()

        bdg.txtPerfilLatitudOut.text = "0.0000"
        bdg.txtPerfilLongitudOut.text = "0.0000"

        bdg.imgLocationUser.setOnClickListener { getLocation() }
        bdg.imgPerfilUsario.setOnClickListener { openPopupMenu(it) }
        bdg.btnPerfilCerrarSession.setOnClickListener { cerrarSesion() }
    }

    private fun cerrarSesion() {
        val databaseRoom = AppDatabaseRoom.getDatabase(requireContext())
        val perfilDao = databaseRoom.perfilDao()
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                perfilDao.delete(usuarios[0])
            }
        }
        val intent = Intent(requireContext(), AuthActivity::class.java)
        startActivity(intent)
        activity?.finish()

    }

    private fun setProfileData() {
        val databaseRoom = AppDatabaseRoom.getDatabase(requireContext())
        val perfilDao = databaseRoom.perfilDao()
        val imgFormat = ImageFormat()
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                usuarios = perfilDao.getAll()
            }
            bdg.txtPerfiloutNombre.text = usuarios[0].nombre
            bdg.txtPerfiloutEmail.text = usuarios[0].correo
            //usuarios[0].imagen?.let {
                //bdg.imgPerfilUsario.setImageBitmap(imgFormat.ByteArrayToBitmap(it.encodeToByteArray()))
            //}
            bdg.txtPerfiloutCompras.text =
                DataBase.compras.filter { it.usuarioId == usuarios[0].uid }.size.toString()
        }
    }

    private fun getLocation() {

        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        val isAuthorized = permissionsManager.permissionsAreGranted(
            requireActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (isAuthorized) {
            requestLocation()
        } else {
            permissionsManager.requestPermissions(
                requireActivity(),
                permissions,
                PermissionsManager.LOCATION_PERMISSION_REQUEST_CODE
            )
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
            } else {
                Toast.makeText(
                    requireContext(),
                    "Permiso de geolocalización denegado!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        if (requestCode == PermissionsManager.REQUEST_IMAGE_CAPTURE_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Permiso de geolocalización denegado!",
                    Toast.LENGTH_SHORT
                ).show()
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
                Log.i(
                    "sportlocation",
                    "requestLocation success: latitude: $latitude, longitude: $longitude"
                )
            }
            .addOnFailureListener { exception: Exception ->
                Log.i("sportlocation", "requestLocation failure: ${exception.message}")
            }
    }

    private fun openPopupMenu(view: View) {
        PopupMenu(requireContext(), view).apply {
            setOnMenuItemClickListener(this@PerfilFragment)
            inflate(R.menu.menu_img_perfil)
            show()
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.imgMnPerfilCamera -> {
                validPermissionCamera()
                true
            }

            R.id.imgMnPerfilGalery -> {
                Toast.makeText(requireContext(), "Pendiente de implementar!", Toast.LENGTH_SHORT).show()
                //validPermissionExternalStorage()
                true
            }

            else -> false
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PermissionsManager.REQUEST_IMAGE_CAPTURE_CODE -> {

                    val databaseRoom = AppDatabaseRoom.getDatabase(requireContext())
                    val perfilDao = databaseRoom.perfilDao()
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    bdg.imgPerfilUsario.setImageBitmap(imageBitmap)
                    val user = PerfilEntity(
                        usuarios[0].uid,
                        usuarios[0].nombre,
                        usuarios[0].correo,
                        usuarios[0].rol,
                        ImageFormat().bitmapToBase64(imageBitmap).decodeToString()
                    )
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            perfilDao.update(user)
                        }
                    }
                }

                PermissionsManager.REQUEST_IMAGE_GALLERY_CODE -> {
                    val imageUri = data?.data
                    // Aquí puedes usar la imagen seleccionada de la galería (imageUri)
                }
            }
        }
    }

    private fun validPermissionCamera() {
        if (permissionsManager.permissionsAreGranted(
                requireActivity(),
                Manifest.permission.CAMERA
            )
        ) {
            openCamera()
        } else {
            permissionsManager.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                PermissionsManager.REQUEST_IMAGE_CAPTURE_CODE
            )
        }
    }

    private fun validPermissionExternalStorage() {
        if (permissionsManager.permissionsAreGranted(
                requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        ) {
            openGallery()
        } else {
            permissionsManager.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PermissionsManager.REQUEST_IMAGE_GALLERY_CODE
            )
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, PermissionsManager.REQUEST_IMAGE_CAPTURE_CODE)
    }

    private fun openGallery() {
        val galleryIntent: Intent
        if (Build.VERSION.SDK_INT < 19) {
            galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
            galleryIntent.type = "image/*"
        } else {
            galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        }
        startActivityForResult(galleryIntent, PermissionsManager.REQUEST_IMAGE_GALLERY_CODE)
    }

}