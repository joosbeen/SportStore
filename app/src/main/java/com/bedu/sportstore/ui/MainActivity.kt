package com.bedu.sportstore.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.room.Database
import com.bedu.sportstore.R
import com.bedu.sportstore.core.notification.NotificationApp
import com.bedu.sportstore.databinding.ActivityMainBinding
import com.bedu.sportstore.db.DataBase
import com.bedu.sportstore.ui.fragments.main.CarritoFragment
import com.bedu.sportstore.ui.main.historial_compra.HistorialComprasFragment
import com.bedu.sportstore.ui.main.home.HomeFragment
import com.bedu.sportstore.ui.main.perfil.PerfilFragment
import com.bedu.sportstore.utileria.PermissionsManager
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.search -> replaceFragment(HistorialComprasFragment())
                R.id.car -> replaceFragment(CarritoFragment())
                R.id.settings -> replaceFragment(PerfilFragment())
                else -> Toast.makeText(this, getString(R.string.not_item_selected), Toast.LENGTH_SHORT).show()

            }

            true
        }

        if (DataBase.productos.size>0) {
            val index = Random.nextInt(0, DataBase.productos.size)
            val producto = DataBase.productos[index]
            val title = "Apresurate se terminan"
            val message = "${producto.nombre} / $${producto.precio}"
            showNotification(this, title, message)
        }

    }

    private fun changeCountCart() {
    }

    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_Layout,fragment)
        fragmentTransaction.commit()

    }

    fun showNotification(context: Context, title: String, message: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Crear un canal de notificación (solo necesario en Android 8.0 y versiones posteriores)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NotificationApp.channelId,
                NotificationApp.channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = NotificationApp.channelDescription
            channel.enableLights(true)
            channel.lightColor = Color.GREEN
            notificationManager?.createNotificationChannel(channel)
        }

        // Construir la notificación
        val notificationBuilder = NotificationCompat.Builder(context, NotificationApp.channelId)
            .setSmallIcon(R.drawable.icon_sport) // Icono de la notificación (debes proporcionar tu propio recurso)
            .setContentTitle(title) // Título de la notificación
            .setContentText(message) // Texto de la notificación
            .setAutoCancel(true) // La notificación se cerrará automáticamente cuando se toque

        // Mostrar la notificación
        notificationManager?.notify(0, notificationBuilder.build())
    }
}

/**
 * https://www.youtube.com/watch?v=IDfO1WB4OOE&ab_channel=WalkiriaApps
 */