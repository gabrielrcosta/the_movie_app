package com.example.mobiletoyou.network

import com.example.mobiletoyou.model.Cast
import com.example.mobiletoyou.model.Movie
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
                    //Toast.makeText(Application.context, ERROR_MSG, Toast.LENGTH_SHORT).show()
                }
            })
    }

    fun getMoviesList(success: OnMoviesListSuccess) {
        RetrofitInitializer().service.getPopularMovies()
            .enqueue(object : Callback<MoviesListResponse>{
                override fun onResponse(
                    call: Call<MoviesListResponse>,
                    response: Response<MoviesListResponse>
                ) {
                    val moviesListResponse = response.body()?.movieList
                    success.onMoviesListResponseSuccess(movieList = moviesListResponse)
                }

                override fun onFailure(call: Call<MoviesListResponse>, t: Throwable) {
                    //Toast.makeText(this@MoviesMenuActivity, "$ERROR_MSG", Toast.LENGTH_SHORT).show()
                }
            })
    }

    fun getCastList(movieId: Int, success: OnMovieCastSuccess) {
        RetrofitInitializer().service.getCastInfo(movieId = movieId).enqueue(
            object : Callback<CastListResponse> {
                override fun onResponse(
                    call: Call<CastListResponse>,
                    response: Response<CastListResponse>
                ) {
                    val castResponse = response.body()?.cast
                    if (castResponse != null) {
                        success.onMovieCastResponseSuccess(cast = castResponse)
                    }
                }
                override fun onFailure(call: Call<CastListResponse>, t: Throwable) {
                    //Toast.makeText(this@CastMovieActivity, ERROR_MSG, Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    interface OnMoviesListSuccess {
        fun onMoviesListResponseSuccess(movieList: MutableList<Movie>?)
    }

    interface OnMovieSuccess {
        fun onMovieDetailsSuccess(movieDetails: MovieDetails?)
    }

    interface OnSuggestedMovieSuccess {
        fun onSuggestedMovieResponseSuccess(suggestedMovie: MutableList<SuggestedMovie>?)
    }

    interface OnMovieCastSuccess {
        fun onMovieCastResponseSuccess(cast: MutableList<Cast>)
    }
}