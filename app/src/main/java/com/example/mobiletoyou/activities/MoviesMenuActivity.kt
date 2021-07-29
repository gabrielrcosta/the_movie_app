package com.example.mobiletoyou.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiletoyou.*
import com.example.mobiletoyou.adapters.MenuMoviesAdapter
import com.example.mobiletoyou.model.Movie
import com.example.mobiletoyou.network.MovieRepository

class MoviesMenuActivity : AppCompatActivity() {
    private val movie: MutableList<Movie> = mutableListOf()
    private lateinit var gridLayoutManager: GridLayoutManager
    private val repository: MovieRepository by lazy {
        MovieRepository()
    }
    private val menuMoviesAdapter: MenuMoviesAdapter by lazy {
        MenuMoviesAdapter(this, movie = movie, getMovieItemClickListener())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_menu)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_menu_movies)
        recyclerView.adapter = menuMoviesAdapter
        gridLayoutManager = GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = gridLayoutManager

        getMoviesList()
    }

    private fun getMoviesList() {
        repository.getMoviesList(object : MovieRepository.OnMoviesListSuccess {
            override fun onMoviesListResponseSuccess(movieList: MutableList<Movie>?) {
                if (movieList != null) {
                    menuMoviesAdapter.setData(movies = movieList)
                }
            }
        })
    }

    private fun getMovieItemClickListener(): MenuMoviesAdapter.MovieItemClickListener {
        return object :
            MenuMoviesAdapter.MovieItemClickListener {
            override fun onItemMovieClicked(id: Int) {
                val intent = Intent(this@MoviesMenuActivity, MainActivity::class.java)
                intent.putExtra(NEXT_MOVIE, id)
                startActivity(intent)
            }

        }
    }
}