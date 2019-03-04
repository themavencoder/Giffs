package com.example.breezil.giffs.di.module


import com.example.breezil.giffs.api.GifApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import javax.inject.Singleton

import com.example.breezil.giffs.BuildConfig.BASE_URL
import com.example.breezil.giffs.api.OkHttp
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideGifApi(): GifApi {
        val okHttp = OkHttp()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttp.getClient())
            .build()
            .create(GifApi::class.java)
    }
}
