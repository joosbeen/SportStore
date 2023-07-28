package com.bedu.sportstore.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bedu.sportstore.model.entity.CarritoEntity
import com.bedu.sportstore.model.entity.CategoriaEntity
import com.bedu.sportstore.model.entity.PerfilEntity
import com.bedu.sportstore.model.entity.ProductoEntity

@Database(
    entities = [
        PerfilEntity::class,
        ProductoEntity::class,
        CategoriaEntity::class,
        CarritoEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabaseRoom : RoomDatabase() {

    abstract fun perfilDao(): PerfilDao
    abstract fun productoDao(): ProductoDao
    abstract fun categoriaDao(): CategoriaDao
    abstract fun carritoDao(): CarritoDao

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