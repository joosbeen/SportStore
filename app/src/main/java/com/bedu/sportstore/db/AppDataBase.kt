package com.bedu.sportstore.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Compra::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun compraDao(): CompraDao
}