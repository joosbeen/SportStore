package com.bedu.sportstore.repository.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bedu.sportstore.model.entity.CarritoEntity

@Dao
interface CarritoDao {

    @Query("SELECT * FROM CarritoEntity")
    suspend fun getAll(): List<CarritoEntity>

    @Insert
    suspend fun insert(carrito: CarritoEntity)

    @Delete
    suspend fun delete(carrito: CarritoEntity)

}