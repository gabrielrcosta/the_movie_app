package com.example.mobiletoyou.network

import android.app.Application
import android.widget.Toast
import com.example.mobiletoyou.API_KEY
import com.example.mobiletoyou.ERROR_MSG
import com.example.mobiletoyou.adapters.MovieSuggestionsAdapter
import com.example.mobiletoyou.model.MovieDetails
import com.example.mobiletoyou.model.SuggestedMovie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository {
    fun getMovieDetails(movieId: Int, success: OnMovieSuccess) {
        RetrofitInitializer().service.getMovieDetails(movieId = movieId)
            .enqueue(object : Callback<MovieDetails> {
                override fun onResponse(
                    call: Call<MovieDetails>,
                    response: Response<MovieDetails>
                ) {
                    val movieDetails = response.body()
                    success.onMovieDetailsSuccess(movieDetails)
                }

                override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                    //Toast.makeText(Application.context, ERROR_MSG, Toast.LENGTH_SHORT).show()
                }
            })
    }

    fun getMovieSuggestions(movieId: Int, success: OnSuggestedMovieSuccess) {
        RetrofitInitializer().service.getMoviesSuggestions(movieId = movieId)
            .enqueue(object : Callback<MoviesSuggestionsResponse> {
                override fun onResponse(
                    call: Call<MoviesSuggestionsResponse>,
                    response: Response<MoviesSuggestionsResponse>
                ) {
                    val listResponse = response.body()?.movies
                    success.onSuggestedMovieResponseSuccess(listResponse)
                }

                override fun onFailure(call: Call<MoviesSuggestionsResponse>, t: Throwable) {
                    //Toast.makeText(this@MainActivity, ERROR_MSG, Toast.LENGTH_SHORT).show()
                }
            })
    }

    interface OnMovieSuccess {
        fun onMovieDetailsSuccess(movieDetails: MovieDetails?)
    }

    interface OnSuggestedMovieSuccess {
        fun onSuggestedMovieResponseSuccess(suggestedMovie: MutableList<SuggestedMovie>?)
    }
}