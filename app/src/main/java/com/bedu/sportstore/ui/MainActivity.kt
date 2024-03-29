package com.bedu.sportstore.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bedu.sportstore.R
import com.bedu.sportstore.core.notification.NotificationApp
import com.bedu.sportstore.databinding.ActivityMainBinding
import com.bedu.sportstore.repository.local.AppDatabaseRoom
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController
    private val productoDao by lazy { AppDatabaseRoom.getDatabase(this).productoDao() }
    private val categoriaDao by lazy { AppDatabaseRoom.getDatabase(this).categoriaDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        //replaceFragment(HomeFragment())

        auth = Firebase.auth

        // config crashlitycs set id user
        auth.currentUser?.uid?.let { FirebaseCrashlytics.getInstance().setUserId(it) }

        /*
        if (DataBase.productos.size > 0) {
            val index = Random.nextInt(0, DataBase.productos.size)
            val producto = DataBase.productos[index]
            val title = "Apresurate se terminan"
            val message = "${producto.nombre} / $${producto.precio}"
            showNotification(this, title, message)
        }
        */

    }

    private fun replaceFragment(fragment: Fragment) {

        /*val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_Layout,fragment)
        fragmentTransaction.commit()*/

    }

    fun showNotification(context: Context, title: String, message: String) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

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