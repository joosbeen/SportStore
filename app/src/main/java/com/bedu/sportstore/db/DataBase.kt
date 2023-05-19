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

    // Lista de productos
    var productos = mutableListOf(
        Producto(
            1, "Sandalias de Tacón Frida", "Kollection para Mujer", 499f, 1,
            "https://cdn1.coppel.com/images/catalog/pr/8500602-1.jpg?iresize=width:300,height:240",
            mutableListOf<String>(
                "https://cdn1.coppel.com/images/catalog/pr/8500602-1.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/8500602-2.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/8500602-3.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/8500602-4.jpg"
            )
        ),
        Producto(
            2, "Tenis Casuales 18", "Forever para Mujer", 299f, 1,
            "https://cdn1.coppel.com/images/catalog/pr/8199752-1.jpg",
            mutableListOf(
                "https://cdn1.coppel.com/images/catalog/pr/8199752-1.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/8199752-2.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/8199752-3.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/8199752-4.jpg"
            )
        ),
        Producto(
            3,
            "Tenis Nike Air Max Alpha",
            "Trainer 5 para Hombr",
            1479f,
            1,
            "https://cdn1.coppel.com/images/catalog/pr/8426492-1.jpg",
            mutableListOf(
                "https://cdn1.coppel.com/images/catalog/pr/8426492-1.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/8426492-2.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/8426492-3.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/8426492-4.jpg"
            )
        ),
        Producto(
            4, "Tenis Nike Giannis Inmortality", "para Hombre", 1689f, 1,
            "https://cdn1.coppel.com/images/catalog/pr/8425812-1.jpg",
            mutableListOf(
                "https://cdn1.coppel.com/images/catalog/pr/8425812-1.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/8425812-2.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/8425812-3.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/8425812-4.jpg"
            )
        ),
        Producto(
            5, "Sandalias de Tacón Frida", "Kollection para Mujer", 499f, 1,
            "https://cdn1.coppel.com/images/catalog/pr/8209642-1.jpg",
            mutableListOf(
                "https://cdn1.coppel.com/images/catalog/pr/8209642-1.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/8209642-2.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/8209642-3.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/8209642-4.jpg"
            )
        ),
        Producto(
            6, "Botines Baby Colors", "para Bebé Niño", 179f, 1,
            "https://cdn1.coppel.com/images/catalog/pr/8263182-1.jpg",
            mutableListOf(
                "https://cdn1.coppel.com/images/catalog/pr/8263182-1.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/8263182-2.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/8263182-3.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/8263182-4.jpg"
            )
        ),
        Producto(
            7, "Playera de Entrenamiento Nike", "para Mujer", 399f, 2,
            "https://cdn1.coppel.com/images/catalog/pr/3597592-2.jpg",
            mutableListOf(
                "https://cdn1.coppel.com/images/catalog/pr/3597592-2.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/3597592-2.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/3597592-4.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/3597592-3.jpg"
            )
        ),
        Producto(
            8, "Pantalón de Entrenamiento Nike Sportswear Club", "para Mujer", 719f, 2,
            "https://cdn1.coppel.com/images/catalog/pr/3597412-1.jpg",
            mutableListOf(
                "https://cdn1.coppel.com/images/catalog/pr/3597412-1.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/3597412-2.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/3597412-3.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/3597412-4.jpg"
            )
        ),
        Producto(
            9, "Sudadera de Entrenamiento Reebok con Capucha", "para Mujer", 729f, 2,
            "https://cdn1.coppel.com/images/catalog/pr/3587782-1.jpg",
            mutableListOf(
                "https://cdn1.coppel.com/images/catalog/pr/3587782-1.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/3587782-2.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/3587782-3.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/3587782-4.jpg"
            )
        ),
    )

    var carrito = mutableListOf(
        CarritoProducto(2,2,2),
        CarritoProducto(5,1,3),
        CarritoProducto(9,3,2)
    )

}