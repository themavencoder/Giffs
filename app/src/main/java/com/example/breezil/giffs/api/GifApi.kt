package com.example.breezil.giffs.api


import com.example.breezil.giffs.model.GifResult
import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Query

interface GifApi {


    @GET("/v1/gifs/trending")
    fun getTrending(@Query("api_key")apikey:String,
                    @Query("limit") limit:Int)
    : Call<GifResult>

    @GET("/v1/gifs/trending")
    fun getTrendingAfter (@Query("api_key")apikey:String,
                    @Query("limit") limit:Int,
                    @Query("offset") offset: Int )
            : Call<GifResult>



}