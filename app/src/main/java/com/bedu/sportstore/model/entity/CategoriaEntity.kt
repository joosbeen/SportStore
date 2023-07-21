package com.bedu.sportstore.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoriaEntity (
    @PrimaryKey val uid: Long,
    @ColumnInfo val nombre: String,
    @ColumnInfo val descripcion: String,
    @ColumnInfo val imagen: String
)