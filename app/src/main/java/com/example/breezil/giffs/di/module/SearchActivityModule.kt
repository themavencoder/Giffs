package com.example.breezil.giffs.di.module

import com.example.breezil.giffs.ui.search.SearchActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SearchActivityModule {
    @ContributesAndroidInjector()
    abstract fun contributeSearchActivity(): SearchActivity
}

