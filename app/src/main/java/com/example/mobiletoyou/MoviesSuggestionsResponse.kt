package com.example.mobiletoyou

import com.google.gson.annotations.SerializedName

class MoviesSuggestionsResponse(
    @SerializedName("results")
    val movies: MutableList<MoviesSuggestionsList>)