package com.example.movieapp

import android.app.Application
import com.example.movieapp.di.netModule
import com.example.movieapp.di.repositoryModule
import com.example.movieapp.di.roomModule
import com.example.movieapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(netModule, viewModelModule, repositoryModule, roomModule)
        }
    }
}