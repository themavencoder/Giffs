package com.example.breezil.giffs.model

import com.google.gson.annotations.SerializedName

data class GifResult(
    @SerializedName("data")
    val `data`: List<Gif>
)