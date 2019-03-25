package com.example.breezil.giffs.ui.trending

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.breezil.giffs.model.Gif
import com.example.breezil.giffs.repository.trends.GifDataSourceFactory
import com.example.breezil.giffs.repository.trends.GiffsDataSource
import com.example.breezil.giffs.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

import javax.inject.Inject

//class MainViewModel @Inject
//constructor(private val trendingRepository: TrendingRepository, application: Application) :
//    AndroidViewModel(application) {
//
//    private var gifList: LiveData<List<Gif>>? = null
//
//    fun getTrending(apikey: String, limit: Int): LiveData<List<Gif>> {
//        if (gifList == null) {
//            gifList = trendingRepository.getTrending(apikey, limit)
//        }
//        return gifList as LiveData<List<Gif>>
//    }
//}
class MainViewModel @Inject constructor(sourceFactory: GifDataSourceFactory, application: Application):
    AndroidViewModel(application){
    var gifList: LiveData<PagedList<Gif>>

    private val compositeDisposable = CompositeDisposable()

    private val pageSize = 10

    private val sourceFactory: GifDataSourceFactory? = null

    init {
//        sourceFactory = GifDataSourceFactory(GifApi.getService(),compositeDisposable)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(true)
            .setPrefetchDistance(pageSize)
            .build()
        gifList = LivePagedListBuilder<Int,Gif>(sourceFactory, config).build()
    }
    fun retry(){
        sourceFactory!!.gifDataSources.value!!.retry()
    }
    fun referesh(){
        sourceFactory!!.gifDataSources.value!!.invalidate()
    }
    fun getNetworkState(): LiveData<NetworkState> = Transformations.switchMap<GiffsDataSource, NetworkState>(
        sourceFactory!!.gifDataSources
    ) {it.networkState}

    fun getRefereshState(): LiveData<NetworkState> = Transformations.switchMap<GiffsDataSource, NetworkState>(
        sourceFactory!!.gifDataSources
    ) { it.initialLoad }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}