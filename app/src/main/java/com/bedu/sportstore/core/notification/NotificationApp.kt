package com.bedu.sportstore.core.notification

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi

class NotificationApp: Application() {

    companion object {
        const val channelId = "Sport_Store"
        const val channelName = "Sport Store"
        const val channelDescription = "Sport Notification"
    }

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setNotificationChannel()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setNotificationChannel() {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, channelName, importance).apply {
            description = channelDescription
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }

}