package com.bedu.sportstore.db

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update

@Dao
interface CompraDao {
    @Query("SELECT * FROM compra")
    suspend fun getCompras(): List<Compra>

    @Insert
    suspend fun insertCompra(compra: Compra)

    @Update
    suspend fun updateCompra(compra: Compra)

    @Delete
    suspend fun deleteCompra(compra: Compra)
}