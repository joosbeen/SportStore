package com.bedu.sportstore.repository.remote.auth

import com.bedu.sportstore.model.request.SigninVO
import com.bedu.sportstore.model.request.SignupVO
import com.bedu.sportstore.model.response.AuthResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthSportStoreService {

    @POST("api/auth/signin.php")
    fun signin(@Body login: SigninVO): Call<AuthResponse>

    @POST("api/auth/signup.php")
    fun signup(@Body register: SignupVO): Call<AuthResponse>

}