package com.example.breezil.giffs.repository.search

import androidx.lifecycle.MutableLiveData
import javax.inject.Inject
import androidx.paging.DataSource
import com.example.breezil.giffs.model.Gif

class SearchDataSourceFactory @Inject
constructor(
    private val dataSource: SearchDataSource
) : DataSource.Factory<Int, Gif>(){


    val gifsDataSourceMutableLiveData: MutableLiveData<SearchDataSource> = MutableLiveData()

    override fun create(): DataSource<Int, Gif> {
        gifsDataSourceMutableLiveData.postValue(dataSource)


        return dataSource
    }

    fun getMutableGifDataSources(): MutableLiveData<SearchDataSource> {
        return gifsDataSourceMutableLiveData
    }

    fun getDataSource(): SearchDataSource {
        return dataSource
    }

}