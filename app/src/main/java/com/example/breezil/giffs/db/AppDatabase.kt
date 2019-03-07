package com.example.breezil.giffs.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.breezil.giffs.R
import com.example.breezil.giffs.model.SavedGif


@Database(entities = arrayOf(SavedGif::class) , version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun savedDao(): SavedDao

    companion object {
        private var appDatabase: AppDatabase? = null

        @Synchronized
        fun getAppDatabase(context: Context): AppDatabase {
            if (appDatabase == null) {
                appDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, context.getString(R.string.giff_db)
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return appDatabase as AppDatabase
        }
    }
}

