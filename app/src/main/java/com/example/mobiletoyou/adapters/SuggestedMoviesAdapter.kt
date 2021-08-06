package com.example.mobiletoyou.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiletoyou.utilities.Constants.MOVIE_URL
import com.example.mobiletoyou.databinding.RowMovieBinding
import com.example.mobiletoyou.model.SuggestedMovie
import com.squareup.picasso.Picasso

class SuggestedMoviesAdapter(
    private val context: Context,
    private val movieSuggestions: MutableList<SuggestedMovie>,
    private val listener: MovieItemClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val rowMovieBinding = RowMovieBinding.inflate(layoutInflater, parent, false)
        return Item(rowMovieBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is Item) {
            val movie = movieSuggestions[position]
            holder.binding(movie)
            holder.itemView.setOnClickListener {
                listener.onItemMovieClicked(movie.id)
            }
        }
    }

    override fun getItemCount(): Int {
        return movieSuggestions.size
    }

    inner class Item(private val rowMovieBinding: RowMovieBinding) :
        RecyclerView.ViewHolder(rowMovieBinding.root) {
        fun binding(suggestedMovie: SuggestedMovie) {
            Picasso.get().load(MOVIE_URL + suggestedMovie.posterPathSuggestion)
                .into(rowMovieBinding.moviesSuggestionsImage)
            rowMovieBinding.moviesSuggestionsTitle.text = suggestedMovie.titleSuggestion
            rowMovieBinding.releaseDate.text = suggestedMovie.releaseDateSuggestion
        }
    }

    fun setData(movies: MutableList<SuggestedMovie>) {
        this.movieSuggestions.addAll(movies)
        notifyDataSetChanged()
    }

    interface MovieItemClickListener {
        fun onItemMovieClicked(id: Int)
    }
}