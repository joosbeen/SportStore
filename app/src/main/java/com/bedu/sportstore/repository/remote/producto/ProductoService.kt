package com.bedu.sportstore.repository.remote.producto

import com.bedu.sportstore.model.response.ProductoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductoService {

    @GET("api/producto/index.php")
    fun getAll(): Call<List<ProductoResponse>>

    @GET("api/producto/index.php")
    fun getFindCategory(@Query("category") category: Int): Call<List<ProductoResponse>>

    @GET("api/producto/index.php")
    fun getFindId(@Query("product") product: Int): Call<ProductoResponse>

}