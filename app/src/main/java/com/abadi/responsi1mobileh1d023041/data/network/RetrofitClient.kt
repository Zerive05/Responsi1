package com.abadi.responsi1mobileh1d023041.data.network // Sesuaikan dengan nama package Anda

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // Base URL dari API, sesuai file JSON Postman
    private const val BASE_URL = "https://api.football-data.org/"

    // Kita buat instance Retrofit-nya
    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            // Tambahkan GSON converter untuk mengubah JSON ke data class
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}