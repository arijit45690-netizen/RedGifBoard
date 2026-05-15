package com.yourname.redgifboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class GifAdapter(
    private val gifs: MutableList<GifItem> = mutableListOf(),
    private val onGifClick: (GifItem) -> Unit
) : RecyclerView.Adapter<GifAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.gifImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.gif_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gif = gifs[position]
        Glide.with(holder.imageView.context)
            .asGif()
            .load(gif.urls.sd)
            .placeholder(android.R.color.darker_gray)
            .error(android.R.color.holo_red_dark)
            .into(holder.imageView)
        holder.imageView.setOnClickListener { onGifClick(gif) }
    }

    override fun getItemCount() = gifs.size

    fun updateGifs(newGifs: List<GifItem>) {
        gifs.clear()
        gifs.addAll(newGifs)
        notifyDataSetChanged()
    }

    fun clearGifs() {
        gifs.clear()
        notifyDataSetChanged()
    }
}
