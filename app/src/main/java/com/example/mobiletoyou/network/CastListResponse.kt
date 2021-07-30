package com.example.mobiletoyou.network

import com.example.mobiletoyou.model.Cast
import com.google.gson.annotations.SerializedName

class CastListResponse(
    @SerializedName("cast")
    val cast: MutableList<Cast>)