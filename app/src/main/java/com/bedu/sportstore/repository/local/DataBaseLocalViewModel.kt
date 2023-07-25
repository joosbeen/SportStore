package com.bedu.sportstore.repository.local

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class DataBaseLocalViewModel(application: Application) : AndroidViewModel(application) {

    private val perfilDao: PerfilDao
    private val productoDao: ProductoDao
    private val categoriaDao: CategoriaDao
    private val carritoDao: CarritoDao

    private val dataBase: AppDatabaseRoom = AppDatabaseRoom.getDatabase(application)

    init {
        perfilDao = dataBase.perfilDao()
        productoDao = dataBase.productoDao()
        categoriaDao = dataBase.categoriaDao()
        carritoDao = dataBase.carritoDao()
    }

    fun perfilDao() = perfilDao

    fun productoDao() = productoDao

    fun categoriaDao() = categoriaDao

    fun carritoDao() = carritoDao


}