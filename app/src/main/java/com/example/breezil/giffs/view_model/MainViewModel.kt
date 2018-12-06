package com.example.breezil.giffs.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.breezil.giffs.model.Gif
import com.example.breezil.giffs.repository.TrendingRepository

import javax.inject.Inject

class MainViewModel @Inject
constructor(private val trendingRepository: TrendingRepository, application: Application) :
    AndroidViewModel(application) {

    private var gifList: LiveData<List<Gif>>? = null

    fun getTrending(apikey: String, limit: Int): LiveData<List<Gif>> {
        if (gifList == null) {
            gifList = trendingRepository.getTrending(apikey, limit)
        }
        return gifList as LiveData<List<Gif>>
    }
}
