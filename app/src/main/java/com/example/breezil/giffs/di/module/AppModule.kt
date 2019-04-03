package com.example.breezil.giffs.di.module


import com.example.breezil.giffs.api.GifApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import javax.inject.Singleton

import com.example.breezil.giffs.BuildConfig.BASE_URL
import com.example.breezil.giffs.api.OkHttp
import io.reactivex.disposables.CompositeDisposable
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideGifApi(): GifApi {
        val okHttp = OkHttp()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttp.getClient())
            .build()
            .create(GifApi::class.java)
    }

    @Provides
    internal fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }


    @Provides
    fun provideSearchString() : String{
        return String()
    }
}
