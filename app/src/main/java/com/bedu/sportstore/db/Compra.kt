package com.bedu.sportstore.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "compra")
data class Compra(
    @PrimaryKey(autoGenerate = true)
    val folio: String,
    val fecha: String,
    val estado: String,
    val costo: Float,
    val usuarioId: Long
) {
    fun add(compra: Compra) {
    }
}