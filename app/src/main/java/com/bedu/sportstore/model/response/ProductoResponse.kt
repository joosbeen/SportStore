package com.bedu.sportstore.model.response

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.bedu.sportstore.model.entity.CarritoEntity
import com.bedu.sportstore.model.entity.ProductoEntity

data class ProductoResponse(
    val id: Int = 0,
    val nombre: String = "",
    val descripcion: String = "",
    val descripcion_larga: String = "",
    val precio: Float = 0f,
    val categoria_id: Int = 0,
    val imagen: String = "",
    val imagenes: String = ""
)

fun ProductoResponse.toProductoEntity(): CarritoEntity {
    return CarritoEntity(
        this.id,
        this.nombre,
        this.descripcion,
        this.descripcion_larga,
        this.precio,
        this.categoria_id,
        this.imagen,
        this.imagenes
    )
}