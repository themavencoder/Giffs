package com.example.breezil.giffs.repository.trends

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.breezil.giffs.model.Gif
import javax.inject.Inject

//class GifDataSourceFactory @Inject constructor(
//    private val gifApi: GifApi,
//    private val compositeDisposable: CompositeDisposable ): DataSource.Factory<String, Gif>() {
//    val sourceLiveData = MutableLiveData<GiffsDataSource>()
//    override fun create(): DataSource<String, Gif> {
//        val source = GiffsDataSource(gifApi,compositeDisposable)
//        sourceLiveData.postValue(source)
//        return source
//    }
//
//}
//class GifDataSourceFactory @Inject constructor(
//    private val gifApi: GifApi,
//    private val compositeDisposable: CompositeDisposable ): DataSource.Factory<String, Gif>() {
//    val sourceLiveData = MutableLiveData<GiffsDataSource>()
//    override fun create(): DataSource<String, Gif> {
//        val source = GiffsDataSource(gifApi,compositeDisposable)
//        sourceLiveData.postValue(source)
//        return source
//    }
//
//}

class GifDataSourceFactory @Inject
constructor(private val dataSource: GiffsDataSource) : DataSource.Factory<Int, Gif>() {
    val gifDataSources: MutableLiveData<GiffsDataSource> = MutableLiveData()

    override fun create(): DataSource<Int, Gif> {
        gifDataSources.postValue(dataSource)


        return dataSource
    }
}