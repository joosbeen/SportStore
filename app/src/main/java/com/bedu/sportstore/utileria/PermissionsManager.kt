package com.bedu.sportstore.utileria

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class PermissionsManager {

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



    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkNotificationPermission(context: Context): Boolean{
        return ActivityCompat
            .checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestPermissions(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            1
        )
    }

    fun executeOrRequestPermission(activity: Activity, callback: () -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!checkNotificationPermission(activity)) {
                requestPermissions(activity)
            } else{
                callback()
            }
        } else {
            callback()
        }
    }

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 123
        const val REQUEST_IMAGE_CAPTURE_CODE = 124
        const val REQUEST_IMAGE_GALLERY_CODE = 225
    }
}