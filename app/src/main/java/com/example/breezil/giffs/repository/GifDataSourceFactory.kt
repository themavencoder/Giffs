package com.example.breezil.giffs.repository

import androidx.lifecycle.MutableLiveData
import com.example.breezil.giffs.api.GifApi
import java.util.concurrent.Executor
import androidx.paging.DataSource
import com.example.breezil.giffs.model.Gif

class GifDataSourceFactory(
    private val gifApi: GifApi,
    private val executor: Executor): DataSource.Factory<String, Gif>() {
    val sourceLiveData = MutableLiveData<GiffsDataSource>()
    override fun create(): DataSource<String, Gif> {
        val source = GiffsDataSource(gifApi,executor)
        sourceLiveData.postValue(source)
        return source
    }

}