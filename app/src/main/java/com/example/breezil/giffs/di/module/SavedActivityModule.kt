package com.example.breezil.giffs.di.module

import com.example.breezil.giffs.ui.SavedActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector



@Module
abstract class SavedActivityModule {
    @ContributesAndroidInjector()
    internal abstract fun contributeSavedActivity(): SavedActivity
}