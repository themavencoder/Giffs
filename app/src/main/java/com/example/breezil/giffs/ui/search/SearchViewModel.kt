package com.example.breezil.giffs.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.breezil.giffs.model.Gif
import com.example.breezil.giffs.repository.NetworkState
import com.example.breezil.giffs.repository.search.SearchDataSource
import com.example.breezil.giffs.repository.search.SearchDataSourceFactory
import com.example.breezil.giffs.repository.search.SearchRepository
import com.example.breezil.giffs.repository.trends.GiffsDataSource
import com.example.breezil.giffs.utils.Constant.Companion.FIVE
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

//class SearchViewModel @Inject
//constructor(private val searchRepository: SearchRepository, application: Application) :
//
//    AndroidViewModel(application){
//    private var gifList: LiveData<List<Gif>>? = null
//    fun getSearch(apikey: String , q:String, limit:Int): LiveData<List<Gif>>{
//        if (gifList == null ){
//            gifList = searchRepository.getSearch(apikey,q,limit)
//        }
//        return gifList as LiveData<List<Gif>>
//    }
//}

class SearchViewModel @Inject
constructor(searchDataSourceFactory: SearchDataSourceFactory, application: Application) :

    AndroidViewModel(application){
    var gifList: LiveData<PagedList<Gif>>

    private val compositeDisposable = CompositeDisposable()

    private val pageSize = 10
    private val searchDataSourceFactory: SearchDataSourceFactory? = null

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(true)
            .setPrefetchDistance(pageSize)
            .build()
        gifList = LivePagedListBuilder<Int,Gif>(searchDataSourceFactory, config).build()
    }

    fun getSearchList(): LiveData<PagedList<Gif>> {
        return gifList
    }


    fun refreshGifs(): LiveData<PagedList<Gif>> {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(true)
            .setPrefetchDistance(pageSize)
            .build()
        gifList = LivePagedListBuilder<Int,Gif>(searchDataSourceFactory!!, config).build()

        return gifList
    }


    fun setParameter(search : String){
        searchDataSourceFactory!!.getDataSource().setSearch(search)
    }

    fun retry(){
        searchDataSourceFactory!!.gifDataSources.value!!.retry()
    }
    fun referesh(){
        searchDataSourceFactory!!.gifDataSources.value!!.invalidate()
    }
    fun getNetworkState(): LiveData<NetworkState> = Transformations.switchMap<SearchDataSource, NetworkState>(
        searchDataSourceFactory!!.gifDataSources
    ) {it.networkState}

    fun getRefereshState(): LiveData<NetworkState> = Transformations.switchMap<SearchDataSource, NetworkState>(
        searchDataSourceFactory!!.gifDataSources
    ) { it.initialLoad }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}