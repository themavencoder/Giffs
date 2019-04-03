package com.example.breezil.giffs.repository.search

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.breezil.giffs.BuildConfig.API_KEY
import com.example.breezil.giffs.api.GifApi
import com.example.breezil.giffs.model.Gif
import com.example.breezil.giffs.model.GifResult
import com.example.breezil.giffs.repository.NetworkState
import com.example.breezil.giffs.repository.PaginationListener
import com.example.breezil.giffs.utils.Constant
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.ArrayList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchDataSource @Inject constructor(
    internal var api: GifApi,
    private val compositeDisposable: CompositeDisposable
): PageKeyedDataSource<Int, Gif>(), PaginationListener<GifResult, Gif> {


    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    var mSearch: String = ""

//    /**
//     * Keep Completable reference for the retry event
//     */
//    private var retryCompletable: Completable? = null
//
//    fun retry() {
//        if (retryCompletable != null) {
//            compositeDisposable.add(retryCompletable!!
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ }, { throwable -> Timber.e(throwable) }))
//        }
//    }

    fun setSearch(search: String) {
        mSearch = search
    }

    fun getSearch(): String {
        return mSearch
    }



    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Gif>) {
        // update network states.
        // we also provide an initial load state to the listeners so that the UI can know when the
        // very first list is loaded.
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        val result : Disposable

        val modelList = ArrayList<Gif>()
        result = api.getSearchs(API_KEY,getSearch() ,params.requestedLoadSize)
            .subscribe({ response ->
                onInitialSuccess(
                    response,
                    callback,
                    modelList
                )
            }, { throwable -> onInitialError(throwable) })

        compositeDisposable.add(result)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Gif>) {
        // set network value to loading.
        networkState.postValue(NetworkState.LOADING)
        val result : Disposable

        val modelList = ArrayList<Gif>()
        result = api.getSearchsAfter(API_KEY,getSearch(),params.requestedLoadSize, params.key)
            .subscribe({ response ->
                onPaginationSuccess(
                    response,
                    callback,
                    params,
                    modelList
                )


            }, { throwable -> onPaginationError(throwable) })
        compositeDisposable.add(result)


    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Gif>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
//    private fun setRetry(action: Action?) {
//        if (action == null) {
//            this.retryCompletable = null
//        } else {
//            this.retryCompletable = Completable.fromAction(action)
//        }
//    }

    override fun onInitialError(throwable: Throwable) {
        initialLoad.postValue(NetworkState(NetworkState.Status.FAILED))
        networkState.postValue(NetworkState(NetworkState.Status.FAILED))
        Timber.e(throwable)
    }

    override fun onInitialSuccess(
        response: GifResult,
        callback: LoadInitialCallback<Int, Gif>,
        results: MutableList<Gif>
    ) {
        if (response.data != null  &&response.data.size > Constant.ZERO) {
            results.addAll(response.data)
            callback.onResult(results, null,27)

            initialLoad.postValue(NetworkState.LOADED)
            networkState.postValue(NetworkState.LOADED)


        } else {
            initialLoad.postValue(NetworkState(NetworkState.Status.NO_RESULT))
            networkState.postValue(NetworkState(NetworkState.Status.NO_RESULT))
        }
    }

    override fun onPaginationError(throwable: Throwable) {
        networkState.postValue(NetworkState(NetworkState.Status.FAILED))
        Timber.e(throwable)
    }

    override fun onPaginationSuccess(
        response: GifResult,
        callback: LoadCallback<Int, Gif>,
        params: LoadParams<Int>,
        results: MutableList<Gif>
    ) {
        if (response.data != null && response.data.size > Constant.ZERO) {
            results.addAll(response.data)

            val key = (if (params.key > 25) params.key + 25 else null)!!.toInt()
            callback.onResult(results, key)

            networkState.postValue(NetworkState.LOADED)
        } else {
            networkState.postValue(NetworkState(NetworkState.Status.NO_RESULT))
        }
    }

    override fun clear() {
        compositeDisposable.clear()
    }
}