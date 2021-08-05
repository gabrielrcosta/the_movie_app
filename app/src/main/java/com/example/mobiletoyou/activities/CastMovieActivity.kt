package com.example.mobiletoyou.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiletoyou.*
//import com.example.mobiletoyou.adapters.CastAdapter
import com.example.mobiletoyou.databinding.ActivityCastMovieBinding
import com.example.mobiletoyou.model.Cast
import com.example.mobiletoyou.model.MovieDetails
import com.example.mobiletoyou.network.MovieRepository
import com.example.mobiletoyou.utilities.ItemClickListener
import com.squareup.picasso.Picasso

//class CastMovieActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityCastMovieBinding
//    private val repository: MovieRepository by lazy {
//        MovieRepository()
//    }
//    private val cast: MutableList<Cast> = mutableListOf()
////    private val castAdapter: CastAdapter by lazy {
////        CastAdapter(this, cast, getCastItemClickListener())
////    }
//    private var castIdOnClick = 0
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityCastMovieBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val recyclerView: RecyclerView = findViewById(R.id.cast_recycler)
////        recyclerView.adapter = castAdapter
//        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
//
//        castIdOnClick = intent.getIntExtra(NEXT_MOVIE, MOVIE_ID)
//
////        getCastList(movieId = castIdOnClick)
//        getMovieDetails(movieId = castIdOnClick)
//    }
//
//    private fun getMovieDetails(movieId: Int) {
//        repository.getMovieDetails(movieId = movieId, object : MovieRepository.OnMovieSuccess{
//            override fun onMovieDetailsSuccess(movieDetails: MovieDetails?) {
//                binding.apply {
//                    Picasso.get().load(MOVIE_URL + movieDetails?.posterPath).into(binding.moviePicture)
//                    titleCastView.text = movieDetails?.title
//                    overviewCastView.text = movieDetails?.overview
//                }
//            }
//        })
//    }
//
////    private fun getCastList(movieId: Int) {
////        repository.getCastList(movieId = movieId, object : MovieRepository.OnMovieCastSuccess {
////            override fun onMovieCastResponseSuccess(cast: MutableList<Cast>) {
////                castAdapter.setData(cast = cast)
////            }
////        })
////    }
//
//    private fun getCastItemClickListener(): ItemClickListener {
//        return object : ItemClickListener {
//            override fun onItemViewClicked(id: Int) {
//                val intent = Intent(this@CastMovieActivity, CastInformationActivity::class.java)
//                intent.putExtra(GET_PERSONAL_ID, id)
//                startActivity(intent)
//                Log.e("Error", "$id")
//            }
//        }
//    }
//}