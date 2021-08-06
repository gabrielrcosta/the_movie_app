package com.example.mobiletoyou.network

import com.example.mobiletoyou.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository {
    fun getMovieDetails(movieId: Int, success: OnSuccess<MovieDetails>) {
        RetrofitInitializer.service.getMovieDetails(movieId = movieId)
            .enqueue(object : Callback<MovieDetails> {
                override fun onResponse(
                    call: Call<MovieDetails>,
                    response: Response<MovieDetails>
                ) {
                    val movieDetails = response.body()
                    if (movieDetails != null) {
                        success.onResponseSuccess(success = movieDetails)
                    }
                }

                override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                    //Toast.makeText(Application.context, ERROR_MSG, Toast.LENGTH_SHORT).show()
                }
            })
    }

    fun getMovieSuggestions(movieId: Int, success: OnSuccess<MutableList<SuggestedMovie>>) {
        RetrofitInitializer.service.getMoviesSuggestions(movieId = movieId)
            .enqueue(object : Callback<MoviesSuggestionsResponse> {
                override fun onResponse(
                    call: Call<MoviesSuggestionsResponse>,
                    response: Response<MoviesSuggestionsResponse>
                ) {
                    val listResponse = response.body()?.movies
                    if (listResponse != null) {
                        success.onResponseSuccess(success = listResponse)
                    }
                }

                override fun onFailure(call: Call<MoviesSuggestionsResponse>, t: Throwable) {
                    //Toast.makeText(Application.context, ERROR_MSG, Toast.LENGTH_SHORT).show()
                }
            })
    }

    fun getMoviesList(success: OnSuccess<MutableList<Movie>>) {
        RetrofitInitializer.service.getPopularMovies()
            .enqueue(object : Callback<MoviesListResponse> {
                override fun onResponse(
                    call: Call<MoviesListResponse>,
                    response: Response<MoviesListResponse>
                ) {
                    val moviesListResponse = response.body()?.movieList
                    if (moviesListResponse != null) {
                        success.onResponseSuccess(success = moviesListResponse)
                    }
                }

                override fun onFailure(call: Call<MoviesListResponse>, t: Throwable) {
                    //Toast.makeText(this@MoviesMenuActivity, "$ERROR_MSG", Toast.LENGTH_SHORT).show()
                }
            })
    }

    fun getCastList(movieId: Int, success: OnSuccess<MutableList<Cast>>) {
        RetrofitInitializer.service.getCastInfo(movieId = movieId).enqueue(
            object : Callback<CastListResponse> {
                override fun onResponse(
                    call: Call<CastListResponse>,
                    response: Response<CastListResponse>
                ) {
                    val castResponse = response.body()?.cast
                    if (castResponse != null) {
                        success.onResponseSuccess(success = castResponse)
                    }
                }

                override fun onFailure(call: Call<CastListResponse>, t: Throwable) {
                    //Toast.makeText(this@CastMovieActivity, ERROR_MSG, Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    fun getPersonalInformation(personId: Int, success: OnSuccess<PersonalInformation>) {
        RetrofitInitializer.service.getPersonalInformation(personId = personId)
            .enqueue(object : Callback<PersonalInformation> {
                override fun onResponse(
                    call: Call<PersonalInformation>,
                    response: Response<PersonalInformation>
                ) {
                    val personDetails = response.body()
                    if (personDetails != null) {
                        success.onResponseSuccess(success = personDetails)
                    }
                }

                override fun onFailure(call: Call<PersonalInformation>, t: Throwable) {
                    //Toast.makeText(this@CastInformationActivity, ERROR_MSG, Toast.LENGTH_SHORT).show()
                }
            })
    }

    interface OnSuccess<T> {
        fun onResponseSuccess(success: T)
    }
}