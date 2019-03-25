package com.example.breezil.giffs.repository.search

import androidx.lifecycle.MutableLiveData
import javax.inject.Inject
import androidx.paging.DataSource
import com.example.breezil.giffs.model.Gif

class SearchDataSourceFactory @Inject
constructor(
    private val dataSource: SearchDataSource,
    private val giffsDataSourceMutableLiveData: MutableLiveData<SearchDataSource>
) : DataSource.Factory<Int, Gif>(){

    val gifDataSources: MutableLiveData<SearchDataSource> = MutableLiveData()

    override fun create(): DataSource<Int, Gif> {
        gifDataSources.postValue(dataSource)


        return dataSource
    }

    fun getMutableGifDataSources(): MutableLiveData<SearchDataSource> {
        return giffsDataSourceMutableLiveData
    }

    fun getDataSource(): SearchDataSource {
        return dataSource
    }

}