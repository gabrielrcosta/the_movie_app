package com.example.mobiletoyou

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val moviesList: MutableList<MoviesSuggestionsList> = mutableListOf()
    private var movieDetails: MovieDetails? = null
    private val moviesSuggestionsAdapter: MovieSuggestionsAdapter by lazy {
        MovieSuggestionsAdapter(this, moviesList, getMovieItemClickListener())
    }
    private var movieClicked = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)

        recyclerView.adapter = moviesSuggestionsAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        movieClicked = intent.getIntExtra(NEXT_MOVIE, MOVIE_ID)

        getMovieDetails(moviesSuggestionsAdapter, movieClicked)
        getMovieSuggestions(moviesSuggestionsAdapter, movieClicked)
    }

    private fun getMovieDetails(moviesSuggestionsAdapter: MovieSuggestionsAdapter, movieId: Int) {
        RetrofitInitializer().service.getMovieDetails(movieId = movieId, apiKey = API_KEY)
            .enqueue(object : Callback<MovieDetails> {
                override fun onResponse(
                    call: Call<MovieDetails>,
                    response: Response<MovieDetails>
                ) {
                    movieDetails = response.body()
                    moviesSuggestionsAdapter.setMovieDetails(movieDetails)
                }

                override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                    Toast.makeText(this@MainActivity, ERROR_MSG, Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun getMovieSuggestions(
        moviesSuggestionsAdapter: MovieSuggestionsAdapter,
        movieId: Int
    ) {
        RetrofitInitializer().service.getMoviesSuggestions(movieId = movieId, apiKey = API_KEY)
            .enqueue(object : Callback<MoviesSuggestionsResponse> {
                override fun onResponse(
                    call: Call<MoviesSuggestionsResponse>,
                    response: Response<MoviesSuggestionsResponse>
                ) {
                    val listResponse = response.body()?.movies
                    if (listResponse != null) {
                        moviesSuggestionsAdapter.setData(listResponse)
                    }
                }

                override fun onFailure(call: Call<MoviesSuggestionsResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity, ERROR_MSG, Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun getMovieItemClickListener(): MovieSuggestionsAdapter.MovieItemClickListener {
        return object :
            MovieSuggestionsAdapter.MovieItemClickListener {
            override fun onItemMovieClicked(id: Int) {
                val intent = Intent(this@MainActivity, CastMovieActivity::class.java)
                intent.putExtra(NEXT_MOVIE, id)
                startActivity(intent)
            }
        }
    }
}