package com.example.mobiletoyou

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesMenuActivity : AppCompatActivity() {
    private val moviesList: MutableList<MoviesList> = mutableListOf()
    private lateinit var gridLayoutManager: GridLayoutManager
    private val menuMoviesAdapter: MenuMoviesAdapter by lazy {
        MenuMoviesAdapter(this, moviesList = moviesList, getMovieItemClickListener())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_menu)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_menu_movies)
        recyclerView.adapter = menuMoviesAdapter
        gridLayoutManager = GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = gridLayoutManager

        getMoviesList(menuMoviesAdapter)
    }

    private fun getMoviesList(menuMoviesAdapter: MenuMoviesAdapter) {
        RetrofitInitializer().service.getPopularMovies(apiKey = API_KEY)
            .enqueue(object : Callback<MoviesListResponse>{
                override fun onResponse(
                    call: Call<MoviesListResponse>,
                    response: Response<MoviesListResponse>
                ) {
                    val moviesListResponse = response.body()?.moviesList
                    if (moviesListResponse != null) {
                        menuMoviesAdapter.setData(moviesListResponse)
                    }
                }

                override fun onFailure(call: Call<MoviesListResponse>, t: Throwable) {
                    Toast.makeText(this@MoviesMenuActivity, "$ERROR_MSG", Toast.LENGTH_SHORT).show()
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