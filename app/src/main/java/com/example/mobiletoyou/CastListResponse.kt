package com.example.mobiletoyou

import com.example.mobiletoyou.model.CastList
import com.google.gson.annotations.SerializedName

class CastListResponse(
    @SerializedName("cast")
    val castList: MutableList<CastList>)