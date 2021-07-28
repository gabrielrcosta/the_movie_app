package com.example.mobiletoyou

import com.example.mobiletoyou.model.Movie
import com.google.gson.annotations.SerializedName

class MoviesListResponse(
    @SerializedName("results")
    val movieList: MutableList<Movie>)