package com.bedu.sportstore.db

import com.bedu.sportstore.R
import com.bedu.sportstore.model.Categoria
import com.bedu.sportstore.model.Producto

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
    /*var compras = mutableListOf(
        Compra("1", "12/12/2022", "Entregado", 523.99F, 2),
        Compra("2", "27/11/2022", "Entregado", 523.99F, 2),
        Compra("3", "23/09/2022", "Entregado", 523.99F, 3),
        Compra("4", "11/05/2022", "Entregado", 523.99F, 2),
        Compra("5", "20/02/2022", "Entregado", 523.99F, 3),
        Compra("6", "01/11/2022", "Entregado", 523.99F, 3),
        Compra("7", "12/01/2022", "Entregado", 523.99F, 3),
        Compra("8", "09/03/2022", "Entregado", 523.99F, 2)
    )*/

    val compras = Compra(
        folio = "123",
        fecha = "27/11/2022",
        estado = "Entregado",
        costo = 999.99F
    )

    // Lista de productos
    var productos = mutableListOf(
        Producto(
            1, "Sandalias de Tacón Frida", "Kollection para Mujer", "Tacones color rosa" , 499f, 1,
            "https://cdn1.coppel.com/images/catalog/pr/8500602-1.jpg?iresize=width:300,height:240",
            mutableListOf<String>(
                "https://cdn1.coppel.com/images/catalog/pr/8500602-1.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/8500602-2.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/8500602-3.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/8500602-4.jpg"
            )
        ),
        Producto(
            2, "Tenis Casuales 18", "Forever para Mujer", "Tenis de piel color blanco", 299f, 1,
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
            "Tenis con la ultima tecnologia para reducir el impacto en la planta del pie",
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
            4, "Tenis Nike Giannis Inmortality", "para Hombre",
            "Tenis color gris con tecnologia para dar tu maximo potencial en tu deporte favorito",
            1689f, 1,
            "https://cdn1.coppel.com/images/catalog/pr/8425812-1.jpg",
            mutableListOf(
                "https://cdn1.coppel.com/images/catalog/pr/8425812-1.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/8425812-2.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/8425812-3.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/8425812-4.jpg"
            )
        ),
        Producto(
            5, "Tenis Sportline para Mujer", "Collection para Mujer",
            "Sal a correr sin perder el estilo con estos atractivos tenis Sportline, perfectos para motivarte durante tu rutina.",
            499f, 1,
            "https://cdn1.coppel.com/images/catalog/pr/8209642-1.jpg",
            mutableListOf(
                "https://cdn1.coppel.com/images/catalog/pr/8209642-1.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/8209642-2.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/8209642-3.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/8209642-4.jpg"
            )
        ),
        Producto(
            6, "Botines Baby Colors", "para Bebé Niño",
            "Calza a tu pequeño con estos elegantes botines Baby Colors, con los que se verá muy galán y moderno.",
            179f, 1,
            "https://cdn1.coppel.com/images/catalog/pr/8263182-1.jpg",
            mutableListOf(
                "https://cdn1.coppel.com/images/catalog/pr/8263182-1.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/8263182-2.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/8263182-3.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/8263182-4.jpg"
            )
        ),
        Producto(
            7, "Playera de Entrenamiento Nike", "para Mujer", "Todo el estilo con esta playera con última técnologia",
            399f, 2,
            "https://cdn1.coppel.com/images/catalog/pr/3597592-2.jpg",
            mutableListOf(
                "https://cdn1.coppel.com/images/catalog/pr/3597592-2.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/3597592-2.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/3597592-4.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/3597592-3.jpg"
            )
        ),
        Producto(
            8, "Pantalón de Entrenamiento Nike Sportswear Club", "para Mujer",
            "Es una prenda diseñada en color negro con el estampado del logo de la marca al frente. Está fabricada a base de 80% algodón y 20% poliéster, por lo que es muy cómoda. Cuenta con cordón en cintura para un ajuste perfecto.",
            719f, 2,
            "https://cdn1.coppel.com/images/catalog/pr/3597412-1.jpg",
            mutableListOf(
                "https://cdn1.coppel.com/images/catalog/pr/3597412-1.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/3597412-2.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/3597412-3.jpg",
                "https://cdn1.coppel.com/images/catalog/pr/3597412-4.jpg"
            )
        ),
        Producto(
            9, "Sudadera de Entrenamiento Reebok con Capucha", "para Mujer",
            "Mantente protegida de las bajas temperaturas con esta increíble sudadera que tiene la marca Reebok.",
            729f, 2,
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
        CarritoProducto(5,1,3)
    )

}