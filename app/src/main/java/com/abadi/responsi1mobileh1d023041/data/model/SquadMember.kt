package com.abadi.responsi1mobileh1d023041.data.model // Sesuaikan dengan nama package Anda

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SquadMember(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("position")
    val position: String?, // Penting untuk pewarnaan

    @SerializedName("dateOfBirth")
    val dateOfBirth: String?,

    @SerializedName("nationality")
    val nationality: String?
) : Parcelable