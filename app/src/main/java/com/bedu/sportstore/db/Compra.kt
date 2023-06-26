package com.bedu.sportstore.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "compra")
data class Compra(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val folio: String,
    val costo: Float,
    val fecha: String,
    val estado: String
) {
    fun add(compra: Compra) {

    }
}