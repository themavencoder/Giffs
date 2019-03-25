package com.example.breezil.giffs.api

import com.example.breezil.giffs.BuildConfig.API_KEY
import com.example.breezil.giffs.model.GifResult
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EndpointRepository @Inject
constructor(private val gifApi: GifApi) {

    fun getTrends(
        limit: Int

    ): Single<GifResult> {
        return gifApi.getTrends(API_KEY, limit)
            .retry(3)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    fun getTrendAfter(
        limit: Int,
        offSet: Int

    ): Single<GifResult> {
        return gifApi.getTrendAfter(API_KEY, limit, offSet)
            .retry(3)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }
}