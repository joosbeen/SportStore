package com.bedu.sportstore.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.bedu.sportstore.R
import com.bedu.sportstore.db.Usuario
import com.bedu.sportstore.model.entity.PerfilEntity
import com.bedu.sportstore.repository.local.AppDatabaseRoom
import com.bedu.sportstore.utileria.UserSession
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        // Inicializando FirebaseApp
        FirebaseApp.initializeApp(this)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAGS_CHANGED
        )

        val databaseRoom = AppDatabaseRoom.getDatabase(this)
        val perfilDao = databaseRoom.perfilDao()
        var usuarios = listOf<PerfilEntity>()
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                usuarios = perfilDao.getAll()
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            if (usuarios.size>0) {
                usuarios[0].let {
                    UserSession.user = Usuario(it.uid, it.nombre, it.correo, "", it.rol)
                }
            }

            val activityClass = if (usuarios.size>0) MainActivity::class.java else AuthActivity::class.java
            val intent= Intent(this, activityClass)
            startActivity(intent)
            finish()
        },3000)
    }

}