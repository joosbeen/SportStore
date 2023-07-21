package com.bedu.sportstore.repository.remote.categoria

import com.bedu.sportstore.model.Categoria
import com.bedu.sportstore.model.response.CategoriaResponse
import retrofit2.Call
import retrofit2.http.GET

interface CategoriaService {

    @GET("api/categoria/index.php")
    fun getAll(): Call<List<CategoriaResponse>>

}