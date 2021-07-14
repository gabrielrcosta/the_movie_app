package com.example.mobiletoyou

import com.google.gson.annotations.SerializedName

data class MoviesList(
    @SerializedName("id")
    val id: Int,
    @SerializedName("poster_path")
    val poster_path: String
)