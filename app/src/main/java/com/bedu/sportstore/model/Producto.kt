package com.bedu.sportstore.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Producto(val id: Int, val nombre: String, val descripcion: String, val descripcionLarga: String, val precio: Float, val categoriaId: Int, val imagen: String, val imagenes:  List<String>) :
    Parcelable
