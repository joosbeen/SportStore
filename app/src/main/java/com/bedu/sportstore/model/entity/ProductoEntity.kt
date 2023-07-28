package com.bedu.sportstore.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductoEntity(
    @PrimaryKey val uid: Long,
    @ColumnInfo val nombre: String,
    @ColumnInfo val descripcion: String,
    @ColumnInfo(name = "descripcion_larga") val descripcionLarga: String,
    @ColumnInfo val precio: Float,
    @ColumnInfo(name = "categoria_id") val categoriaId: Int,
    @ColumnInfo val imagen: String,
    @ColumnInfo val imagenes: String
)