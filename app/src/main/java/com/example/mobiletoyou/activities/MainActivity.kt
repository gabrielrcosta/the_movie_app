package com.example.mobiletoyou.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiletoyou.*
import com.example.mobiletoyou.adapters.MovieSuggestionsAdapter
import com.example.mobiletoyou.model.MovieDetails
import com.example.mobiletoyou.model.SuggestedMovie
import com.example.mobiletoyou.network.MovieRepository
import com.example.mobiletoyou.network.MoviesSuggestionsResponse
import com.example.mobiletoyou.network.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val moviesList: MutableList<SuggestedMovie> = mutableListOf()
    private val repository: MovieRepository by lazy {
        MovieRepository()
    }
    private val moviesSuggestionsAdapter: MovieSuggestionsAdapter by lazy {
        MovieSuggestionsAdapter(this, moviesList, getMovieItemClickListener())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)

        recyclerView.adapter = moviesSuggestionsAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val movieId = intent.getIntExtra(NEXT_MOVIE, MOVIE_ID)

        getMovieDetails(movieId)
        getSuggestedMovies(movieId)
    }

    private fun getMovieDetails(movieId: Int) {
        repository.getMovieDetails(movieId = movieId, object: MovieRepository.OnMovieSuccess{
            override fun onMovieDetailsSuccess(movieDetails: MovieDetails?) {
                moviesSuggestionsAdapter.setMovieDetails(movieDetails)
            }
        })
    }

    private fun getSuggestedMovies(movieId: Int) {
        repository.getMovieSuggestions(movieId = movieId, object: MovieRepository.OnSuggestedMovieSuccess{
            override fun onSuggestedMovieResponseSuccess(suggestedMovie: MutableList<SuggestedMovie>?) {
                if (suggestedMovie != null) {
                    moviesSuggestionsAdapter.setData(suggestedMovie)
                }
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