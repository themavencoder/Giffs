package com.example.breezil.giffs

import android.app.Activity
import android.app.Application
import com.example.breezil.giffs.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector

import javax.inject.Inject

class GiffsApp : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
//        DaggerAppComponent.builder()
//            .application(this)
//            .build()
//            .inject(this)
        AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}
