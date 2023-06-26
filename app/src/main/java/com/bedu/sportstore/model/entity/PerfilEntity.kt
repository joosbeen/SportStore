package com.bedu.sportstore.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PerfilEntity(
    @PrimaryKey val uid: Long,
    @ColumnInfo val nombre: String,
    @ColumnInfo val correo: String,
    @ColumnInfo val rol: String,
    @ColumnInfo val imagen: String?
)