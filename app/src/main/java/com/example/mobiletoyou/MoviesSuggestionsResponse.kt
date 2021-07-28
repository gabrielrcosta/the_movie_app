package com.example.mobiletoyou

import com.example.mobiletoyou.model.SuggestedMovie
import com.google.gson.annotations.SerializedName

class MoviesSuggestionsResponse(
    @SerializedName("results")
    val movies: MutableList<SuggestedMovie>)