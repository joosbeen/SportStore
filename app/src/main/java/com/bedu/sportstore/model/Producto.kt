package com.bedu.sportstore.db

data class Producto(val id: Int, val nombre: String, val descripcion: String, val descripcionLarga: String, val precio: Float, val categoriaId: Int, val imagen: String, val imagenes:  List<String>)
