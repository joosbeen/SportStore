package com.bedu.sportstore.utileria

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class PermissionsManager {

    //private lateinit var locationClient: FusedLocationProviderClient

    fun requestPermissions(activity: Activity, permissions: Array<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode)
    }

    fun arePermissionsGranted(grantResults: IntArray): Boolean {
        for (result in grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    fun permissionsAreGranted(activity: Activity, accessFineLocation: String): Boolean =
        (ContextCompat.checkSelfPermission(
            activity,
            accessFineLocation
        ) == PackageManager.PERMISSION_GRANTED)

    fun locationClient(activity: Activity) = LocationServices.getFusedLocationProviderClient(activity)
        /*locationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // Obtener las coordenadas de la ubicación
                val latitude = location?.latitude
                val longitude = location?.longitude

                // Hacer algo con las coordenadas
                // ...
            }
            .addOnFailureListener { exception: Exception ->
                // Manejar errores al obtener la ubicación
                // ...
            }*/

    /*fun permissionsAreGranted(activity: Activity): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {


        }
    }*/

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 123
        const val REQUEST_IMAGE_CAPTURE_CODE = 124
        const val REQUEST_IMAGE_GALLERY_CODE = 225
    }
}