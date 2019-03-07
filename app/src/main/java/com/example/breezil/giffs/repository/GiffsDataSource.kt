package com.example.breezil.giffs.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.example.breezil.giffs.BuildConfig.API_KEY
import com.example.breezil.giffs.api.GifApi
import com.example.breezil.giffs.model.Gif
import com.example.breezil.giffs.model.GifResult
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.Executor

class GiffsDataSource(
    private val gifApi : GifApi,
    private val executor : Executor) : ItemKeyedDataSource<String, Gif>() {

    private var retry: (() -> Any)? = null

    private var offset : Int  = 0

    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()
    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            executor.execute {
                it.invoke()
            }
        }
    }


    override fun loadInitial(params: LoadInitialParams<String>,
                             callback: LoadInitialCallback<Gif>) {
        val request = gifApi.getTrending(
            API_KEY,
            limit = params.requestedLoadSize
        )
        // update network states.
        // we also provide an initial load state to the listeners so that the UI can know when the
        // very first list is loaded.
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        // triggered by a refresh, we better execute sync
        try {
            val response = request.execute()
            val items = response.body()?.data?.map { it } ?: emptyList()
            retry = null
            networkState.postValue(NetworkState.LOADED)
            initialLoad.postValue(NetworkState.LOADED)
            callback.onResult(items)
        } catch (ioException: IOException) {
            retry = {
                loadInitial(params, callback)
            }
            val error = NetworkState.error(ioException.message ?: "unknown error")
            networkState.postValue(error)
            initialLoad.postValue(error)
        }

    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<Gif>) {
        networkState.postValue(NetworkState.LOADING)
        gifApi.getTrendingAfter(
            API_KEY,
            limit = params.requestedLoadSize,
            offset = offset + 25).enqueue(object : retrofit2.Callback<GifResult>{
            override fun onResponse(call: Call<GifResult>,
                                    response: Response<GifResult>) {
                if(response.isSuccessful){
                    val items = response.body()?.data?.map { it } ?: emptyList()
                    retry = null
                    callback.onResult(items)
                    networkState.postValue(NetworkState.LOADED)
                }else{
                    retry = {
                        loadAfter(params,callback)
                    }
                    networkState.postValue(
                        NetworkState.error("error code: ${response.code()}")
                    )
                }

            }

            override fun onFailure(call: Call<GifResult>, t: Throwable) {
                retry= {
                    loadAfter(params,callback)
                }
                networkState.postValue(NetworkState.error(t.message ?: "unknown err"))
            }
        })
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<Gif>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getKey(item: Gif): String = item.title

}