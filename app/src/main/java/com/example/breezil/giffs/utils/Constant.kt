package com.example.breezil.giffs.utils

import com.example.breezil.giffs.BuildConfig

class Constant{
    val API_KEY = BuildConfig.API_KEY
    var BASE_URL = BuildConfig.BASE_URL
    var GIF = "single_gif"

    var SINGLE_PHOTO = "single_photo"

    companion object {
        @JvmField val GIF = "single_gif"
    }
}
