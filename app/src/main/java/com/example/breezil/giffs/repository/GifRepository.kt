package com.example.breezil.giffs.repository

import com.example.breezil.giffs.model.Gif

interface GifRepository {
    fun getGif(pageSize: Int): Listing<Gif>
}