package com.example.mobiletoyou

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

private const val viewItemCount = 1
private const val TYPE_HEADER = 0
private const val TYPE_ITEM = 1

class MovieSuggestionsAdapter(
    private val context: Context,
    private val movieSuggestions: MutableList<MoviesSuggestionsList>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = if (viewType == TYPE_HEADER) {
            LayoutInflater.from(context).inflate(R.layout.item_header_movie, parent, false)
        } else {
            LayoutInflater.from(context).inflate(R.layout.row_movie, parent, false)
        }
        return if (viewType == TYPE_HEADER) {
            Header(view)
        } else {
            Item(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is Header) {
            holder.movieLiked.visibility = View.INVISIBLE
            Picasso.get().load(movieURL + movieDetail?.posterPath).into(holder.posterPath)
            holder.likes.text = movieDetail?.voteCount.toString()
            holder.title.text = movieDetail?.title
            holder.overview.text = movieDetail?.overview
            holder.popularity.text = movieDetail?.popularity.toString()
        } else if (holder is Item) {
            val movie = movieSuggestions[position - viewItemCount]
            movie.apply {
                Picasso.get().load(movieURL + movie.posterPathSuggestion).into(holder.posterPathSuggestion)
                holder.titleMoviesSuggestion.text = movie.titleSuggestion
                holder.releaseDate.text = movie.releaseDateSuggestion
            }
        }
    }

    override fun getItemCount(): Int {
        return movieSuggestions.size + viewItemCount
    }

    internal inner class Item(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val posterPathSuggestion: ImageView = itemView.findViewById(R.id.movies_suggestions_image)
        val titleMoviesSuggestion: TextView = itemView.findViewById(R.id.movies_suggestions_title)
        val releaseDate: TextView = itemView.findViewById(R.id.release_date)
    }

    internal inner class Header(headerView: View) : RecyclerView.ViewHolder(headerView) {
        val posterPath: ImageView = itemView.findViewById(R.id.background_picture)
        val likes: TextView = itemView.findViewById(R.id.likes)
        val overview: TextView = itemView.findViewById(R.id.overview)
        val title: TextView = itemView.findViewById(R.id.title)
        val popularity: TextView = itemView.findViewById(R.id.views_text)
        val likesIcon: ImageView = itemView.findViewById(R.id.like_icon)
        val movieLiked: ImageView = itemView.findViewById(R.id.like_movie_click)
    }

    var movieDetail: MovieDetails? = null

    fun setMovieDetails(movieDetails: MovieDetails?) {
        this.movieDetail = movieDetails
        notifyDataSetChanged()
    }

    fun setData(movies: MutableList<MoviesSuggestionsList>) {
        this.movieSuggestions.addAll(movies)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            TYPE_HEADER
        } else {
            TYPE_ITEM
        }
    }
}