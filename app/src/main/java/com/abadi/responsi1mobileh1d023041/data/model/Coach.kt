package com.abadi.responsi1mobileh1d023041.data.model // Sesuaikan dengan nama package Anda

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coach(
    @SerializedName("id")
    val id: Int?, // Boleh null untuk jaga-jaga

    @SerializedName("name")
    val name: String,

    @SerializedName("dateOfBirth")
    val dateOfBirth: String?,

    @SerializedName("nationality")
    val nationality: String?
) : Parcelable