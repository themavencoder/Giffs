package com.example.breezil.giffs.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.breezil.giffs.model.Gif
import com.example.breezil.giffs.repository.SearchRepository
import javax.inject.Inject

class SearchViewModel @Inject
constructor(private val searchRepository: SearchRepository, application: Application) :

    AndroidViewModel(application){
    private var gifList: LiveData<List<Gif>>? = null
    fun getSearch(apikey: String , q:String, limit:Int): LiveData<List<Gif>>{
        if (gifList == null ){
            gifList = searchRepository.getSearch(apikey,q,limit)
        }
        return gifList as LiveData<List<Gif>>
    }
}