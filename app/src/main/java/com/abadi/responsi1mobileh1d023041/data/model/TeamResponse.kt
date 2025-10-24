package com.abadi.responsi1mobileh1d023041.data.model // Sesuaikan dengan nama package Anda

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TeamResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("crest") // JSON-nya 'crest'
    val crestUrl: String, // Kita beri nama 'crestUrl'

    @SerializedName("coach")
    val coach: Coach,

    @SerializedName("squad")
    val squad: List<SquadMember>
) : Parcelable