package com.example.mobiletoyou

import retrofit2.Call
import retrofit2.http.GET

interface Service {
    @GET("3/movie/337404?api_key=1f6b7aa9053b85e9c0749cae0bf8524b")
    fun getMovieDetails(): Call<MovieDetails>
    @GET("3/movie/337404/similar?api_key=1f6b7aa9053b85e9c0749cae0bf8524b&page=1")
    fun getMoviesSuggestions(): Call<MoviesSuggestionsResponse>
}
