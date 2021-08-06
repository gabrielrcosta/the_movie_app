package com.example.mobiletoyou.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiletoyou.utilities.Constants.MOVIE_URL
import com.example.mobiletoyou.databinding.MenuMoviesBinding
import com.example.mobiletoyou.model.Movie
import com.squareup.picasso.Picasso

class MenuMoviesAdapter(
    private val context: Context,
    val movie: MutableList<Movie>,
    private val listener: MovieItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val menuMoviesBinding = MenuMoviesBinding.inflate(layoutInflater, parent, false)
        return Item(menuMoviesBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is Item) {
            val movie = movie[position]
            holder.binding(movie = movie)
            holder.itemView.setOnClickListener {
                listener.onItemMovieClicked(id = movie.id)
            }
        }
    }

    inner class Item(private val menuMoviesBinding: MenuMoviesBinding) :
        RecyclerView.ViewHolder(menuMoviesBinding.root) {
        fun binding(movie: Movie) {
            Picasso.get().load(MOVIE_URL + movie.poster_path).into(menuMoviesBinding.posterPath)
        }
    }

    override fun getItemCount(): Int {
        return movie.size
    }

    fun setData(movies: MutableList<Movie>) {
        this.movie.addAll(movies)
        notifyDataSetChanged()
    }

    interface MovieItemClickListener {
        fun onItemMovieClicked(id: Int)
    }
}