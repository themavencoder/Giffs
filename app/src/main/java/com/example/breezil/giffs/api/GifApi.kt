package com.example.breezil.giffs.api

import com.example.breezil.giffs.BuildConfig.BASE_URL
import com.example.breezil.giffs.model.GifResult
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.http.GET
import retrofit2.http.Query

interface GifApi {


    @GET("/v1/gifs/trending")
    fun getTrending(@Query("api_key")apikey:String,
                    @Query("limit") limit:Int)
    : Call<GifResult>



    @GET("/v1/gifs/search")
    fun getSearch(@Query("api_key") apikey: String,
                  @Query("q") q : String,
                  @Query("limit") limit:Int
                  ) : Call<GifResult>




    @GET("/v1/gifs/trending")
    fun getTrends(@Query("api_key")apikey:String,
                  @Query("limit") limit:Int)
            : Single<GifResult>

    @GET("/v1/gifs/trending")
    fun getTrendAfter(@Query("api_key")apikey:String,
                  @Query("limit") limit:Int,
                  @Query("offset") offset: Int)
            : Single<GifResult>


    @GET("/v1/gifs/search")
    fun getSearchs(@Query("api_key") apikey: String,
                  @Query("q") q : String,
                  @Query("limit") limit:Int
    ) : Single<GifResult>

    @GET("/v1/gifs/search")
    fun getSearchsAfter(@Query("api_key") apikey: String,
                       @Query("q") q : String,
                       @Query("limit") limit:Int,
                       @Query("offset") offset: Int
    ) : Single<GifResult>


    companion object {
        fun getService(): GifApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(GifApi::class.java)
        }
    }


}