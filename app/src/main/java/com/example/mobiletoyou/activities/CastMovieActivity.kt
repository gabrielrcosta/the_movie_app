package com.example.mobiletoyou.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiletoyou.*
import com.example.mobiletoyou.adapters.CastAdapter
import com.example.mobiletoyou.databinding.ActivityCastMovieBinding
import com.example.mobiletoyou.model.CastList
import com.example.mobiletoyou.model.MovieDetails
import com.example.mobiletoyou.network.CastListResponse
import com.example.mobiletoyou.network.RetrofitInitializer
import com.example.mobiletoyou.utilities.ItemClickListener
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CastMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCastMovieBinding
    private val castList: MutableList<CastList> = mutableListOf()
    private var movieDetails: MovieDetails? = null
    private val castAdapter: CastAdapter by lazy {
        CastAdapter(this, castList = castList, getCastItemClickListener())
    }
    private var castIdOnClick = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCastMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView: RecyclerView = findViewById(R.id.cast_recycler)
        recyclerView.adapter = castAdapter
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        castIdOnClick = intent.getIntExtra(NEXT_MOVIE, MOVIE_ID)

        getCastList(castAdapter = castAdapter, movieId = castIdOnClick)
        getMovieDetails(movieId = castIdOnClick)
    }

    private fun getMovieDetails(movieId: Int) {
        RetrofitInitializer().service.getMovieDetails(movieId = movieId, apiKey = API_KEY)
            .enqueue(object : Callback<MovieDetails> {
                override fun onResponse(
                    call: Call<MovieDetails>,
                    response: Response<MovieDetails>
                ) {
                    movieDetails = response.body()
                    binding.apply {
                        Picasso.get().load(MOVIE_URL + movieDetails?.posterPath).into(moviePicture)
                        titleCastView.text = movieDetails?.title
                        overviewCastView.text = movieDetails?.overview
                    }
                }

                override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                    Toast.makeText(this@CastMovieActivity, ERROR_MSG, Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun getCastList(castAdapter: CastAdapter, movieId: Int) {
        RetrofitInitializer().service.getCastInfo(movieId = movieId, apiKey = API_KEY).enqueue(
            object : Callback<CastListResponse> {
                override fun onResponse(
                    call: Call<CastListResponse>,
                    response: Response<CastListResponse>
                ) {
                    val castResponse = response.body()?.castList
                    if (castResponse != null) {
                        castAdapter.setData(castResponse)
                    }
                }
                override fun onFailure(call: Call<CastListResponse>, t: Throwable) {
                    Toast.makeText(this@CastMovieActivity, ERROR_MSG, Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private fun getCastItemClickListener(): ItemClickListener {
        return object : ItemClickListener {
            override fun onItemViewClicked(id: Int) {
                val intent = Intent(this@CastMovieActivity, CastInformationActivity::class.java)
                intent.putExtra(GET_PERSONAL_ID, id)
                startActivity(intent)
                Log.e("Error", "$id")
            }
        }
    }
}