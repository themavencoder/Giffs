package com.example.breezil.giffs.callbacks

import com.example.breezil.giffs.model.SavedGif

interface SavedGifClickListener {
    fun clickGif(savedGif: SavedGif)
}