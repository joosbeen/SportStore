package com.bedu.sportstore.model.response

data class ProductoResponse(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val descripcion_larga: String,
    val precio: Float,
    val categoria_id: Int,
    val imagen: String,
    val imagenes: String
)
