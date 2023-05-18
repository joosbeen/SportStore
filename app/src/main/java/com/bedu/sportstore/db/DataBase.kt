package com.bedu.sportstore.db

import com.bedu.sportstore.R

object DataBase {

    // Lista de usuarios
    var usuarios = mutableListOf(
        Usuario(1, "Jose Gusman Alcantaras", "jose@gmail.com", "12345", "admin"),
        Usuario(2, "Carlos Enrique Segoviano", "carlos@gmail.com", "Carlos12", "cliente"),
        Usuario(3, "Juan Luis Tarso Lopez", "juan@gmail.com", "Juan123", "cliente"),
        Usuario(3, "Amanda Hernadez Tom", "admin@gmail.com", "admin123", "admin")
    )

    // Lista de categorias
    var categorias = mutableListOf<Categoria>(
        Categoria(1, "Calzado", "", R.drawable.tenis),
        Categoria(1, "Ropa", "", R.drawable.ropa)
    )

    // Lista de Compras
    var compras = mutableListOf(
        Compra("1", "12/12/2022", "Entregado", 523.99F, 2),
        Compra("2", "27/11/2022", "Entregado", 523.99F, 2),
        Compra("3", "23/09/2022", "Entregado", 523.99F, 3),
        Compra("4", "11/05/2022", "Entregado", 523.99F, 2),
        Compra("5", "20/02/2022", "Entregado", 523.99F, 3),
        Compra("6", "01/11/2022", "Entregado", 523.99F, 3),
        Compra("7", "12/01/2022", "Entregado", 523.99F, 3),
        Compra("8", "09/03/2022", "Entregado", 523.99F, 2)
    )

}