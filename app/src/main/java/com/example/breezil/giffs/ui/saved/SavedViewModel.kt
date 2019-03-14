package com.example.breezil.giffs.ui.saved

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.breezil.giffs.db.AppDatabase
import com.example.breezil.giffs.model.Gif
import com.example.breezil.giffs.model.SavedGif
import com.example.breezil.giffs.repository.SavedRepository
import javax.inject.Inject



class SavedViewModel @Inject
constructor(@NonNull application: Application) : AndroidViewModel(application) {
    private val savedRepository: SavedRepository
    val savedList: LiveData<List<SavedGif>>
    private val appDatabase: AppDatabase
    private var savedImageModel: LiveData<Gif>? = null

    init {

        savedRepository = SavedRepository(application)
        savedList = savedRepository.allSaved
        appDatabase = AppDatabase.getAppDatabase(this.getApplication())
    }

    fun insert(savedGif: SavedGif) {
        savedRepository.insert(savedGif)
    }

    fun delete(savedGif: SavedGif) {
        savedRepository.delete(savedGif)
    }

    fun deleteAll() {
        savedRepository.deleteAllSaved()
    }

//    fun getSavedList(): LiveData<List<Gif>> {
//        return savedList
//    }



}