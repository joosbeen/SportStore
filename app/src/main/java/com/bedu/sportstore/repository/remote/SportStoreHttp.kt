package com.bedu.sportstore.repository.remote

import com.bedu.sportstore.repository.remote.auth.AuthSportStoreService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class SportStoreHttp {

    companion object {

        private var okHttpClient = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        private var gson: Gson = GsonBuilder()
            .setLenient()
            .create()

        private var retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://sport-store-bedu.000webhostapp.com/")
            .build()

        fun authHttp(): AuthSportStoreService = retrofit.create(AuthSportStoreService::class.java)

    }
}