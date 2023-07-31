package com.bedu.sportstore.repository.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bedu.sportstore.model.entity.PerfilEntity

@Dao
interface PerfilDao {

    @Query("SELECT * FROM PerfilEntity")
    fun getAll(): List<PerfilEntity>

    @Insert
    fun insert(perfil: PerfilEntity)

    @Insert
    fun insertAll(vararg perfil: PerfilEntity)

    @Delete
    fun delete(perfil: PerfilEntity)

    @Update
    fun update(perfil: PerfilEntity)

}