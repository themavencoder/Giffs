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
import com.example.breezil.giffs.utils.AppExecutors
import com.example.breezil.giffs.utils.Constant.Companion.FIVE
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class SearchViewModel @Inject
constructor(
    private val searchDataSourceFactory: SearchDataSourceFactory,
    private val appExecutors: AppExecutors,
    application: Application) :

    AndroidViewModel(application){
    var gifList: LiveData<PagedList<Gif>>

    private val pageSize = 10

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(true)
            .setPrefetchDistance(pageSize)
            .build()
        gifList = LivePagedListBuilder<Int,Gif>(searchDataSourceFactory, config)
            .setFetchExecutor(appExecutors.networkIO())
            .build()
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
        gifList = LivePagedListBuilder<Int,Gif>(searchDataSourceFactory, config)
            .setFetchExecutor(appExecutors.networkIO())
            .build()

        return gifList
    }


    fun setParameter(search : String){
        searchDataSourceFactory.getDataSource().setSearch(search)
    }

//    fun retry(){
//        searchDataSourceFactory!!.gifsDataSourceMutableLiveData.value!!.retry()
//    }
//    fun referesh(){
//        searchDataSourceFactory!!.gifsDataSourceMutableLiveData.value!!.invalidate()
//    }
//    fun getNetworkState(): LiveData<NetworkState> = Transformations.switchMap<SearchDataSource, NetworkState>(
//        searchDataSourceFactory!!.gifsDataSourceMutableLiveData
//    ) {it.networkState}
//
//    fun getRefereshState(): LiveData<NetworkState> = Transformations.switchMap<SearchDataSource, NetworkState>(
//        searchDataSourceFactory!!.gifsDataSourceMutableLiveData
//    ) { it.initialLoad }

//    override fun onCleared() {
//        super.onCleared()
//        compositeDisposable.dispose()
//    }

}