package com.example.mobiletoyou

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiletoyou.model.CastList
import com.squareup.picasso.Picasso

class CastAdapter(
    private val context: Context,
    private val castList: MutableList<CastList>,
    private val listener: ItemClickListener
    ) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_cast, parent, false)
        return Item(view)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       if (holder is Item){
            val cast = castList[position]
            cast.apply {
                Picasso.get().load(MOVIE_URL + cast.profilePath).into(holder.profilePath)
                holder.name.text = cast.name
                holder.itemView.setOnClickListener {
                    listener.onItemViewClicked(id = cast.id)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return castList.size
    }

    internal inner class Item(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profilePath: ImageView = itemView.findViewById(R.id.cast_picture)
        val name: TextView = itemView.findViewById(R.id.cast_name)
    }

    fun setData(cast: MutableList<CastList>) {
        this.castList.addAll(cast)
        notifyDataSetChanged()
    }
}