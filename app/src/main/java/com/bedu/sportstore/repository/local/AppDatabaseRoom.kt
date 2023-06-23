package com.bedu.sportstore.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bedu.sportstore.model.entity.PerfilEntity

@Database(
    entities = [
        PerfilEntity::class
    ],
    version = 1
)
abstract class AppDatabaseRoom : RoomDatabase() {

    abstract fun perfilDao(): PerfilDao

    companion object {


        private const val DB_NAME = "db_movie_app.db"
        private var INSTANCE: AppDatabaseRoom? = null

        fun getDatabase(context: Context): AppDatabaseRoom {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabaseRoom::class.java,
                    "sportstore"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }


    }
}