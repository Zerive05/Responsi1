package com.abadi.responsi1mobileh1d023041.data.network // Sesuaikan dengan nama package Anda

import com.abadi.responsi1mobileh1d023041.data.model.TeamResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    // Endpoint ini sesuai soal, mengambil data tim spesifik
    // ID 98 adalah untuk AC Milan
    @GET("v4/teams/98")
    suspend fun getTeamData(
        // Token WAJIB ada di header
        @Header("X-Auth-Token") token: String
    ): Response<TeamResponse> // Kita pakai Response<> agar bisa cek error
}