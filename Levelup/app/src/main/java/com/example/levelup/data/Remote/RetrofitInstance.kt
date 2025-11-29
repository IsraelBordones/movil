package com.example.levelup.data.Remote // 👈 Esta línea es la que hace la magia

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.levelup.data.Remote.ApiService // Asegúrate de que ApiService también tenga su paquete bien puesto

object RetrofitInstance {
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}