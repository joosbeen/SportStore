package com.bedu.sportstore.ui

import android.app.Application
import com.bedu.sportstore.repository.local.AppDatabaseRoom

class SportApplication : Application() {

    private val database by lazy { AppDatabaseRoom.getDatabase(this) }
    val perfilDao
        get() = database.perfilDao()

}