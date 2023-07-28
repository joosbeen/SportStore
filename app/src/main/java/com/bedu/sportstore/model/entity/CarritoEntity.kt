package com.bedu.sportstore.model.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.bedu.sportstore.model.response.ProductoResponse

@Entity
data class CarritoEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Int? = null,
    val id: Int = 0,
    val nombre: String = "",
    val descripcion: String = "",
    val descripcion_larga: String = "",
    val precio: Float = 0f,
    val categoria_id: Int = 0,
    val imagen: String = "",
    val imagenes: String = ""
)