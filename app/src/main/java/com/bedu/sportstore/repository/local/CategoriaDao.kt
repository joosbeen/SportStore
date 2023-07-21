package com.bedu.sportstore.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bedu.sportstore.model.entity.CategoriaEntity
import com.bedu.sportstore.model.entity.PerfilEntity

@Dao
interface CategoriaDao {

    @Query("SELECT * FROM CategoriaEntity")
    fun getAll(): List<CategoriaEntity>

    @Insert
    suspend fun insert(categoria: CategoriaEntity)
}