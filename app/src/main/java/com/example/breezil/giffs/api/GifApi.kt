package com.example.breezil.giffs.api


import com.example.breezil.giffs.model.GifResult
import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Query

interface GifApi {


    @GET("/v1/gifs/trending")
    fun getTrending(@Query("api_key")apikey:String,
                    @Query("limit") limit:Int = 5)
    : Call<GifResult>

//
//    @GET("/v1/gifs/trending")
//    fun getTrending(@Query("limit") limit:Int = 5)
//            : Deferred<GifResult>
//
//
//    companion object {
//        operator fun invoke(
//            connectivityInterceptor: ConnectivityInterceptor
//        ): GifApi{
//            val requestInterceptor = Interceptor{ chain ->
//                val url = chain.request()
//                    .url()
//                    .newBuilder()
//                    .addQueryParameter("api_key",API_KEY)
//                    .build()
//                val request =  chain.request()
//                    .newBuilder()
//                    .url(url)
//                    .build()
//                return@Interceptor chain.proceed(request)
//            }
//
//            val okHttpClient = OkHttpClient.Builder()
//
//                .addInterceptor(requestInterceptor)
//                .connectTimeout(1, TimeUnit.MINUTES)
//                .readTimeout(1, TimeUnit.MINUTES)
//                .writeTimeout(1, TimeUnit.MINUTES)
//                .retryOnConnectionFailure(true)
//                .build()
//
//
//            return  Retrofit.Builder()
//                .client(okHttpClient)
//                .baseUrl(BASE_URL)
//                .addCallAdapterFactory(CoroutineCallAdapterFactory())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(GifApi::class.java)
//         }
//
//    }

}