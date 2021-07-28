package com.example.mobiletoyou.model

import com.google.gson.annotations.SerializedName

class CastList(
    @SerializedName("id")
    val id: Int,
    @SerializedName("profile_path")
    val profilePath: String,
    @SerializedName("name")
    val name: String)