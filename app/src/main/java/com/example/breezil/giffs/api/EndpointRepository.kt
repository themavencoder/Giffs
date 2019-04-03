package com.example.breezil.giffs.api

import com.example.breezil.giffs.BuildConfig.API_KEY
import com.example.breezil.giffs.model.GifResult
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton



@Singleton
class EndpointRepository @Inject
constructor(private val gifApi: GifApi) {

    fun getTrends(
        api_key :String,
        limit: Int

    ): Single<GifResult> {
        return gifApi.getTrends(API_KEY, limit)
            .retry(3)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    }

    fun getTrendAfter(
        api_key :String,
        limit: Int,
        offSet: Int

    ): Single<GifResult> {
        return gifApi.getTrendAfter(api_key, limit, offSet)
            .retry(3)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }


    fun getSearchAfter(
        api_key :String,
        q:String,
        limit: Int,
        offSet: Int
    ):Single<GifResult>{
        return gifApi.getSearchsAfter(api_key,q,limit,offSet)
    }


}