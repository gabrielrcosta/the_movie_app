package com.example.mobiletoyou

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val moviesList: MutableList<MoviesSuggestionsList> = mutableListOf()
    private var movieDetails: MovieDetails? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val moviesSuggestionsAdapter = MovieSuggestionsAdapter(this, moviesList)

        recyclerView.adapter = moviesSuggestionsAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: Service = retrofit.create(Service::class.java)

        service.getMovieDetails().enqueue(object : Callback<MovieDetails> {
            override fun onResponse(
                call: Call<MovieDetails>,
                response: Response<MovieDetails>
            ) {
                movieDetails = response.body()
                moviesSuggestionsAdapter.setMovieDetails(movieDetails)
            }

            override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                Toast.makeText(this@MainActivity, errorMSG, Toast.LENGTH_SHORT).show()
            }
        })

        val moviesService: Service = retrofit.create(Service::class.java)

        moviesService.getMoviesSuggestions().enqueue(object : Callback<MoviesSuggestionsResponse>{
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
                Toast.makeText(this@MainActivity, errorMSG, Toast.LENGTH_SHORT).show()
            }
        })
        }
    }