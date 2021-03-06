package com.example.breezil.giffs.di


import android.app.Application
import com.example.breezil.giffs.GiffsApp
import com.example.breezil.giffs.di.module.AppModule
import com.example.breezil.giffs.di.module.MainActivityModule
import com.example.breezil.giffs.di.module.SavedActivityModule
import com.example.breezil.giffs.di.module.SearchActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule

import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class,
                        AppModule::class,
                        MainActivityModule::class,
                        SearchActivityModule::class,
                        SavedActivityModule::class ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(giffsApp: GiffsApp)
}
