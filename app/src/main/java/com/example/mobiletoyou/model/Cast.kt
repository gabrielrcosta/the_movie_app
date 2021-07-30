package com.example.mobiletoyou.model

import com.google.gson.annotations.SerializedName

class Cast(
    @SerializedName("id")
    val id: Int,
    @SerializedName("profile_path")
    val profilePath: String,
    @SerializedName("name")
    val name: String)