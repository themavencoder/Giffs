package com.example.breezil.giffs.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.example.breezil.giffs.BuildConfig
import com.example.breezil.giffs.R
import com.example.breezil.giffs.callbacks.SavedGifClickListener
import com.example.breezil.giffs.databinding.GifItemBinding
import com.example.breezil.giffs.glide.GlideApp
import com.example.breezil.giffs.model.SavedGif

class SavedGifRecyclerAdapter(internal var context: Context, internal var savedGifClickListener: SavedGifClickListener) :
    ListAdapter<SavedGif, SavedGifRecyclerAdapter.SavedGiffHolder>(DIFF_CALLBACK) {
    internal var gif: SavedGif? = null
    internal lateinit var binding: GifItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedGiffHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = GifItemBinding.inflate(layoutInflater, parent, false)


        return SavedGiffHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedGiffHolder, position: Int) {
        val gif = getItem(position)
        holder.bind(gif, savedGifClickListener)
    }

    inner class SavedGiffHolder(var binding: GifItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(savedGif: SavedGif, listener: SavedGifClickListener) {
            itemView.setOnClickListener { v -> savedGifClickListener.clickGif(savedGif) }

            val gif_image : String = BuildConfig.START_GIF + gif!!.id + BuildConfig.END_GIF_200




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

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SavedGif>() {
            override fun areItemsTheSame(oldItem: SavedGif, newItem: SavedGif): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SavedGif, newItem: SavedGif): Boolean {
                return (oldItem.id == newItem.id
                        && oldItem.title == newItem.title
                        && oldItem.slug == newItem.slug)
            }
        }
    }
}
