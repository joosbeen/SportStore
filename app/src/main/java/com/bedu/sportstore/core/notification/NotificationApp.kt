package com.bedu.sportstore.core.notification

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bedu.sportstore.R
import com.bedu.sportstore.ui.main.productdetail.ProductDetailFragment

class NotificationApp: Application() {

    companion object {
        const val notifylId = 20
        const val channelId = "default_notification_channel_id"
        const val channelName = "Sport_Store"
        const val channelDescription = "Sport_Notification"
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


    @SuppressLint("MissingPermission")
    fun simpleNotification(context: Context, title: String, content: String){
        with(context) {
            val builder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.icon_sport)
                .setColor(getColor(R.color.dark_bg_li))
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            //lanzamos la notificación
            NotificationManagerCompat
                .from(this)
                .notify(notifylId, builder.build()) // en este caso pusimos un id genérico
        }
    }

    @SuppressLint("MissingPermission")
    fun touchNotification(activity: Activity, title: String, content: String) {
        // Un PendingIntent para dirigirnos a una actividad pulsando la notificación
        val intent = Intent(activity, ProductDetailFragment::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            activity,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(activity, channelId)
            .setSmallIcon(R.drawable.icon_sport)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent) // se define aquí el content intend
            .setAutoCancel(true) // la notificación desaparece al dar click sobre ella

        with(NotificationManagerCompat.from(activity)) {
            notify(notifylId, builder.build())
        }
    }


    fun showNotification(context: Context, title: String, message: String) {
        // ID del canal de notificación (debe ser único)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Crear un canal de notificación (solo necesario en Android 8.0 y versiones posteriores)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = channelDescription
            channel.enableLights(true)
            channel.lightColor = Color.GREEN
            notificationManager?.createNotificationChannel(channel)
        }

        // Construir la notificación
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.icon_sport) // Icono de la notificación (debes proporcionar tu propio recurso)
            .setContentTitle(title) // Título de la notificación
            .setContentText(message) // Texto de la notificación
            .setAutoCancel(true) // La notificación se cerrará automáticamente cuando se toque

        // Mostrar la notificación
        notificationManager?.notify(0, notificationBuilder.build())
    }

}