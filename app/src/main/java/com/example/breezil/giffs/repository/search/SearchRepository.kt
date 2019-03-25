package com.example.breezil.giffs.repository.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.breezil.giffs.api.GifApi
import com.example.breezil.giffs.model.Gif
import com.example.breezil.giffs.model.GifResult
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton
import retrofit2.Callback
import retrofit2.Response


@Singleton
class SearchRepository@Inject
internal constructor(private val gifApi: GifApi) {

    private var gifs: MediatorLiveData<List<Gif>>?= null

    fun getSearch(apikey:String, q:String, limit:Int): LiveData<List<Gif>>{
        if(gifs == null){
            gifs = MediatorLiveData()

            gifApi.getSearch(apikey,q,limit).enqueue(object : Callback<GifResult> {
                override fun onResponse(call: Call<GifResult>, response: Response<GifResult>) {
                    gifs!!.setValue(response.body()!!.data)
                }

                override fun onFailure(call: Call<GifResult>, t: Throwable) {

                }

            })
        }
        return gifs as MediatorLiveData<List<Gif>>
    }

}