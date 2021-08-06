package com.example.mobiletoyou.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiletoyou.Constants.MOVIE_URL
import com.example.mobiletoyou.databinding.RowCastBinding
import com.example.mobiletoyou.model.Cast
import com.example.mobiletoyou.utilities.ItemClickListener
import com.squareup.picasso.Picasso

class CastAdapter(
    private val context: Context,
    private val cast: MutableList<Cast>,
    private val listener: ItemClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val rowCastBinding = RowCastBinding.inflate(layoutInflater, parent, false)
        return Item(rowCastBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is Item) {
            val cast = cast[position]
            holder.binding(cast = cast)
            holder.itemView.setOnClickListener {
                listener.onItemViewClicked(id = cast.id)
            }
        }
    }

    override fun getItemCount(): Int {
        return cast.size
    }

    inner class Item(private val rowCastBinding: RowCastBinding) :
        RecyclerView.ViewHolder(rowCastBinding.root) {
        fun binding(cast: Cast) {
            Picasso.get().load(MOVIE_URL + cast.profilePath).into(rowCastBinding.castPicture)
            rowCastBinding.castName.text = cast.name
        }
    }

    fun setCastData(cast: MutableList<Cast>) {
        this.cast.addAll(cast)
        notifyDataSetChanged()
    }
}