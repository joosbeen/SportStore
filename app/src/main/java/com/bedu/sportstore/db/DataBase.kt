package com.bedu.sportstore.db

import com.bedu.sportstore.R

object DataBase {

    var usuarios = mutableListOf(
        Usuario(1, "Jose", "jose@gmail.com", "12345", "admin"),
        Usuario(2, "Carlos", "carlos@gmail.com", "Carlos12", "cliente"),
        Usuario(3, "Juan", "juan@gmail.com", "Juan123", "cliente"),
        Usuario(3, "Super Admin", "admin@gmail.com", "admin123", "admin")
    )

    var categorias = mutableListOf<Categoria>(
        Categoria(1, "Calzado", "", R.drawable.tenis),
        Categoria(1, "Ropa", "", R.drawable.ropa)
    )

}