package com.example.breezil.giffs.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.breezil.giffs.db.AppDatabase
import com.example.breezil.giffs.db.SavedDao
import com.example.breezil.giffs.model.Gif
import com.example.breezil.giffs.model.SavedGif
import javax.inject.Inject



class SavedRepository @Inject
constructor(application: Application) {
    private val savedDao: SavedDao
    val allSaved: LiveData<List<SavedGif>>

    init {
        val database = AppDatabase.getAppDatabase(application)
        savedDao = database.savedDao()
        allSaved = savedDao.images
    }

    fun insert(savedGif: SavedGif) {
        InsertSaved(savedDao).execute(savedGif)
    }

    fun delete(savedGif: SavedGif) {
        DeleteSaved(savedDao).execute(savedGif)
    }

    fun deleteAllSaved() {
        DeleteAllSaved(savedDao).execute()
    }

    private class InsertSaved(private val savedDao: SavedDao) : AsyncTask<SavedGif, Void, Void>() {

         override fun doInBackground(vararg savedGif: SavedGif): Void? {
            savedDao.insert(savedGif[0])
            return null
        }
    }

    private class DeleteSaved(private val savedDao: SavedDao) : AsyncTask<SavedGif, Void, Void>() {

         override fun doInBackground(vararg savedGif: SavedGif): Void? {
            savedDao.delete(savedGif[0])
            return null
        }
    }

    private class DeleteAllSaved(private val savedDao: SavedDao) : AsyncTask<Void, Void, Void>() {

         override fun doInBackground(vararg voids: Void): Void? {
            savedDao.deleteAllGifs()
            return null
        }
    }

}
