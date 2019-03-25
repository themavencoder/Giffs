package com.example.breezil.giffs.repository.trends


import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.breezil.giffs.api.GifApi
import com.example.breezil.giffs.model.Gif
import com.example.breezil.giffs.model.GifResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrendingRepository @Inject
internal constructor(private val gifApi: GifApi) {

    private var gifs: MediatorLiveData<List<Gif>>? = null

    fun getTrending(apikey: String, limit: Int): LiveData<List<Gif>> {
        if (gifs == null) {
            gifs = MediatorLiveData()

            gifApi.getTrending(apikey, limit).enqueue(object : Callback<GifResult> {
                override fun onResponse(call: Call<GifResult>, response: Response<GifResult>) {
                    gifs!!.value = response.body()!!.data
                }

                override fun onFailure(call: Call<GifResult>, t: Throwable) {

                }
            })
        }

        return gifs as MediatorLiveData<List<Gif>>
    }
}
