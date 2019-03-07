package com.example.breezil.giffs.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.example.breezil.giffs.BuildConfig.*
import com.example.breezil.giffs.R
import com.example.breezil.giffs.model.Gif
import com.example.breezil.giffs.callbacks.GifClickListener
import com.example.breezil.giffs.databinding.GifItemBinding
import com.example.breezil.giffs.glide.GlideApp

class GifRecyclerViewAdapter(internal var context: Context, internal var gifClickListener: GifClickListener) :
    ListAdapter<Gif, GifRecyclerViewAdapter.GiffHolder>(DIFF_CALLBACK) {
    internal var gif: Gif? = null
    internal lateinit var binding: GifItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiffHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = GifItemBinding.inflate(layoutInflater, parent, false)


        return GiffHolder(binding)
    }

    override fun onBindViewHolder(holder: GiffHolder, position: Int) {
        val gif = getItem(position)
        holder.bind(gif, gifClickListener)
    }

    inner class GiffHolder(var binding: GifItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(gif: Gif, listener: GifClickListener) {
            itemView.setOnClickListener { v -> gifClickListener.clickGif(gif) }

            val gif_image : String = START_GIF + gif.id + END_GIF_200




            GlideApp.with(context)
                .asGif()
                .load(gif_image)
                .thumbnail(0.1f)
                .apply(
                    RequestOptions()
                        .centerCrop()
                        .fitCenter()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                )
                .into(binding.gifImage)

        }
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Gif>() {
            override fun areItemsTheSame(oldItem: Gif, newItem: Gif): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Gif, newItem: Gif): Boolean {
                return (oldItem.id == newItem.id
                        && oldItem.title == newItem.title
                        && oldItem.slug == newItem.slug)
            }
        }
    }
}
