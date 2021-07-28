package com.example.mobiletoyou.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiletoyou.MOVIE_URL
import com.example.mobiletoyou.R
import com.example.mobiletoyou.model.Movie
import com.squareup.picasso.Picasso

class MenuMoviesAdapter(
    private val context: Context,
    val movie: MutableList<Movie>,
    private val listener: MovieItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.menu_movies, parent, false)
        return Item(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is Item) {
            val movie = movie[position]
            Picasso.get().load(MOVIE_URL + movie.poster_path).into(holder.posterPath)
            holder.itemView.setOnClickListener {
                listener.onItemMovieClicked(id = movie.id)
            }
        }
    }

    override fun getItemCount(): Int {
        return movie.size
    }

    internal inner class Item(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val posterPath: ImageView = itemView.findViewById(R.id.poster_path)
    }

    fun setData(movies: MutableList<Movie>) {
        this.movie.addAll(movies)
        notifyDataSetChanged()
    }

    interface MovieItemClickListener {
        fun onItemMovieClicked(id: Int)
    }
}