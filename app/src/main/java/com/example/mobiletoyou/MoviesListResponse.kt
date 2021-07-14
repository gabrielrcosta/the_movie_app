package com.example.mobiletoyou

import com.google.gson.annotations.SerializedName

class MoviesListResponse(
    @SerializedName("results")
    val moviesList: MutableList<MoviesList>)