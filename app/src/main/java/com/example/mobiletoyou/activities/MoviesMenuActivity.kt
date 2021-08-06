package com.example.mobiletoyou.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobiletoyou.*
import com.example.mobiletoyou.utilities.Constants.NEXT_MOVIE
import com.example.mobiletoyou.adapters.MenuMoviesAdapter
import com.example.mobiletoyou.databinding.ActivityMoviesMenuBinding
import com.example.mobiletoyou.model.Movie
import com.example.mobiletoyou.network.MovieRepository

class MoviesMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMoviesMenuBinding
    private val movie: MutableList<Movie> = mutableListOf()
    private lateinit var gridLayoutManager: GridLayoutManager
    private val repository: MovieRepository by lazy {
        MovieRepository()
    }
    private val menuMoviesAdapter: MenuMoviesAdapter =
        MenuMoviesAdapter(this, movie = movie, getMovieItemClickListener())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdapter()
        getMoviesList()
    }

    private fun setupAdapter() {
        binding.recyclerMenuMovies.adapter = menuMoviesAdapter
        gridLayoutManager = GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false)
        binding.recyclerMenuMovies.layoutManager = gridLayoutManager
    }

    private fun getMoviesList() {
        repository.getMoviesList(object : MovieRepository.OnSuccess<MutableList<Movie>> {
            override fun onResponseSuccess(success: MutableList<Movie>) {
                menuMoviesAdapter.setData(movies = success)
            }
        })
    }

    private fun getMovieItemClickListener(): MenuMoviesAdapter.MovieItemClickListener {
        return object :
            MenuMoviesAdapter.MovieItemClickListener {
            override fun onItemMovieClicked(id: Int) {
                val intent = Intent(this@MoviesMenuActivity, MovieDetailsActivity::class.java)
                intent.putExtra(NEXT_MOVIE, id)
                startActivity(intent)
            }
        }
    }
}