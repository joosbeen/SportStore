package com.bedu.sportstore.db

object DataBase {

    var usuarios = mutableListOf(
        Usuario(1, "Jose", "jose@gmail.com", "12345", "admin"),
        Usuario(2, "Carlos", "carlos@gmail.com", "Carlos12", "cliente"),
        Usuario(3, "Juan", "juan@gmail.com", "Juan123", "cliente"),
        Usuario(3, "Super Admin", "admin@gmail.com", "admin123", "admin")
    )

}