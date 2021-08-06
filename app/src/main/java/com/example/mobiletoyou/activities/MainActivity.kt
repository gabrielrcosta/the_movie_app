package com.example.mobiletoyou.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiletoyou.*
import com.example.mobiletoyou.Constants.GET_PERSONAL_ID
import com.example.mobiletoyou.Constants.MOVIE_ID
import com.example.mobiletoyou.Constants.MOVIE_URL
import com.example.mobiletoyou.Constants.NEXT_MOVIE
import com.example.mobiletoyou.adapters.CastAdapter
import com.example.mobiletoyou.adapters.MovieSuggestionsAdapter
import com.example.mobiletoyou.databinding.ActivityMainBinding
import com.example.mobiletoyou.model.Cast
import com.example.mobiletoyou.model.MovieDetails
import com.example.mobiletoyou.model.SuggestedMovie
import com.example.mobiletoyou.network.MovieRepository
import com.example.mobiletoyou.utilities.ItemClickListener
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val moviesList: MutableList<SuggestedMovie> = mutableListOf()
    private val castList: MutableList<Cast> = mutableListOf()
    private val repository: MovieRepository by lazy {
        MovieRepository()
    }
    private val moviesSuggestionsAdapter: MovieSuggestionsAdapter by lazy {
        MovieSuggestionsAdapter(this, moviesList, getMovieItemClickListener())
    }
    private val castAdapter = CastAdapter(this, castList, getCastItemClickListener())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.castRecyclerView.adapter = castAdapter
        binding.castRecyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        binding.recyclerView.adapter = moviesSuggestionsAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        val movieId = intent.getIntExtra(NEXT_MOVIE, MOVIE_ID)

        getMovieDetails(movieId = movieId)
        getCastList(movieId = movieId)
        getSuggestedMovies(movieId = movieId)
    }

    private fun getMovieDetails(movieId: Int) {
        repository.getMovieDetails(movieId = movieId, object: MovieRepository.OnMovieSuccess{
            override fun onMovieDetailsSuccess(movieDetails: MovieDetails?) {
                binding.apply {
                    layoutIncluded.apply {
                        Picasso.get().load(MOVIE_URL + movieDetails?.posterPath).into(backgroundPicture)
                        likeMovieClick.visibility = View.INVISIBLE
                        likes.text = movieDetails?.voteCount.toString()
                        title.text = movieDetails?.title
                        overview.text = movieDetails?.overview
                        viewsText.text = movieDetails?.popularity.toString()
                        likeMovie.setOnClickListener {
                            likeMovie.visibility =
                                View.INVISIBLE; likeMovieClick.visibility = View.VISIBLE; likes.text =
                            movieDetails?.voteCount?.plus(1).toString()
                        }
                        likeMovieClick.setOnClickListener {
                            likeMovieClick.visibility =
                                View.INVISIBLE; likeMovie.visibility =
                            View.VISIBLE; likes.text =
                            movieDetails?.voteCount.toString()
                        }
                    }
                }
            }
        })
    }

    private fun getCastList(movieId: Int) {
        repository.getCastList(movieId = movieId, object : MovieRepository.OnMovieCastSuccess {
            override fun onMovieCastResponseSuccess(cast: MutableList<Cast>) {
                castAdapter.setCastData(cast)
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
                val intent = Intent(this@MainActivity, MainActivity::class.java)
                intent.putExtra(NEXT_MOVIE, id)
                startActivity(intent)
            }
        }
    }

    private fun getCastItemClickListener(): ItemClickListener {
        return object : ItemClickListener {
            override fun onItemViewClicked(id: Int) {
                val intent = Intent(this@MainActivity, CastInformationActivity::class.java)
                intent.putExtra(GET_PERSONAL_ID, id)
                startActivity(intent)
                Log.e("Error", "$id")
            }
        }
    }
}