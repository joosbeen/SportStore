package com.bedu.sportstore.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bedu.sportstore.model.entity.ProductoEntity

@Dao
interface ProductoDao {

    @Query("SELECT * FROM ProductoEntity")
    suspend fun getAll(): List<ProductoEntity>

    @Insert
    suspend fun insert(producto: ProductoEntity)

    @Insert
    suspend fun insertAll(vararg producto: ProductoEntity)
}