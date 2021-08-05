package com.example.mobiletoyou.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiletoyou.MOVIE_URL
import com.example.mobiletoyou.R
import com.example.mobiletoyou.model.SuggestedMovie
import com.squareup.picasso.Picasso

class MovieSuggestionsAdapter(
    private val context: Context,
    private val movieSuggestions: MutableList<SuggestedMovie>,
    private val listener: MovieItemClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_movie, parent, false)
        return Item(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is Item) {
            val movie = movieSuggestions[position]
            movie.apply {
                Picasso.get().load(MOVIE_URL + movie.posterPathSuggestion)
                    .into(holder.posterPathSuggestion)
                holder.titleMoviesSuggestion.text = movie.titleSuggestion
                holder.releaseDate.text = movie.releaseDateSuggestion
            }
            holder.itemView.setOnClickListener {
                listener.onItemMovieClicked(movie.id)
            }
        }
    }

    override fun getItemCount(): Int {
        return movieSuggestions.size
    }

    internal inner class Item(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val posterPathSuggestion: ImageView = itemView.findViewById(R.id.movies_suggestions_image)
        val titleMoviesSuggestion: TextView = itemView.findViewById(R.id.movies_suggestions_title)
        val releaseDate: TextView = itemView.findViewById(R.id.release_date)
    }

    fun setData(movies: MutableList<SuggestedMovie>) {
        this.movieSuggestions.addAll(movies)
        notifyDataSetChanged()
    }

    interface MovieItemClickListener {
        fun onItemMovieClicked(id: Int)
    }
}