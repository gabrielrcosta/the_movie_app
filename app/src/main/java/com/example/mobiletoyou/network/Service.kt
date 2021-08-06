package com.example.mobiletoyou.network

import com.example.mobiletoyou.utilities.Constants.API_KEY
import com.example.mobiletoyou.model.MovieDetails
import com.example.mobiletoyou.model.PersonalInformation
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Service {
    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Call<MovieDetails>

    @GET("movie/{movie_id}/similar")
    fun getMoviesSuggestions(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Call<MoviesSuggestionsResponse>

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = API_KEY
    ): Call<MoviesListResponse>

    @GET("movie/{movie_id}/credits")
    fun getCastInfo(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Call<CastListResponse>

    @GET("person/{person_id}")
    fun getPersonalInformation(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Call<PersonalInformation>
}
