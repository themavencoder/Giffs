package com.example.breezil.giffs.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.breezil.giffs.model.SavedGif


@Dao
interface SavedDao {

    @get:Transaction
    @get:Query("SELECT * FROM saved_gifs_table ORDER BY saved_id DESC ")
    val images: LiveData<List<SavedGif>>


    @Insert
    fun insert(savedGif: SavedGif)

    @Query("DELETE FROM saved_gifs_table")
    fun deleteAllGifs()

    @Delete
    abstract fun delete(savedGif: SavedGif)

    @Query("SELECT * FROM saved_gifs_table WHERE saved_id = :saved_id")
    abstract fun getSaveById(saved_id: Int): LiveData<SavedGif>
}