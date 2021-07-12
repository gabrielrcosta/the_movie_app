package com.example.mobiletoyou

import com.google.gson.annotations.SerializedName

data class MoviesSuggestionsList(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val titleSuggestion: String,
    @SerializedName("release_date")
    val releaseDateSuggestion: String,
    @SerializedName("poster_path")
    val posterPathSuggestion: String
)