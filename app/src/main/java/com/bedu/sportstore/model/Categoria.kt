package com.bedu.sportstore.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Categoria(val id: Int = 0, val nombre: String = "", val descripcion: String = "", val imagen: Int = 0) :
    Parcelable
