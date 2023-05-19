package com.bedu.sportstore.db

import com.bedu.sportstore.utileria.UserSession
import java.util.Date

data class CarritoProducto(val id: Long, val productoId: Int, val usuarioId: Long)

fun CarritoProducto.cart(): List<CarritoProducto> {
    val carrito = DataBase.carrito.filter { it.usuarioId.toLong() == UserSession.user?.id }
    return carrito
}

fun CarritoProducto.add(productoId: Int) {
    val userId: Long = UserSession.user?.id ?: 0
    val item = CarritoProducto(Date().time, productoId, userId)
    DataBase.carrito.add(item)
}