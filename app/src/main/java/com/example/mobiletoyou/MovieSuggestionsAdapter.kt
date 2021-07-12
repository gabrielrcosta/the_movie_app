package com.example.mobiletoyou

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

private const val VIEW_ITEM_COUNT = 1
private const val TYPE_HEADER = 0
private const val TYPE_ITEM = 1

class MovieSuggestionsAdapter(
    private val context: Context,
    private val movieSuggestions: MutableList<MoviesSuggestionsList>,
    private val listener: MovieItemClickListener
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
            Picasso.get().load(MOVIE_URL + movieDetail?.posterPath).into(holder.posterPath)
            holder.likes.text = movieDetail?.voteCount.toString()
            holder.title.text = movieDetail?.title
            holder.overview.text = movieDetail?.overview
            holder.popularity.text = movieDetail?.popularity.toString()
            holder.movieUnliked.setOnClickListener {holder.movieUnliked.visibility =
                View.INVISIBLE; holder.movieLiked.visibility = View.VISIBLE; holder.likes.text =
                movieDetail?.voteCount?.plus(1).toString()
            }
            holder.movieLiked.setOnClickListener { holder.movieLiked.visibility =
                View.INVISIBLE; holder.movieUnliked.visibility = View.VISIBLE; holder.likes.text =
                movieDetail?.voteCount.toString()
            }

        } else if (holder is Item) {
            val movie = movieSuggestions[position - VIEW_ITEM_COUNT]
            movie.apply {
                Picasso.get().load(MOVIE_URL + movie.posterPathSuggestion).into(holder.posterPathSuggestion)
                holder.titleMoviesSuggestion.text = movie.titleSuggestion
                holder.releaseDate.text = movie.releaseDateSuggestion
            }
            holder.itemView.setOnClickListener {
                listener.onItemMovieClicked(movie.id)
            }
        }
    }

    override fun getItemCount(): Int {
        return movieSuggestions.size + VIEW_ITEM_COUNT
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
        val movieUnliked: ImageView = itemView.findViewById(R.id.like_movie)
        val movieLiked: ImageView = itemView.findViewById(R.id.like_movie_click)
    }

    private var movieDetail: MovieDetails? = null

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

    interface MovieItemClickListener {
        fun onItemMovieClicked(id: Int)
    }
}