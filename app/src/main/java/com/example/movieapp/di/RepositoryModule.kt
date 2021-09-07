package com.example.movieapp.di

import android.content.Context
import com.example.movieapp.api.MovieApiService
import com.example.movieapp.dto.movie.MovieDao
import com.example.movieapp.repository.MainRepository
import com.example.movieapp.repository.MainRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val repositoryModule = module {
    single<MainRepository> { provideRepository(get(), get(), androidApplication()) }
}


fun provideRepository(apiService: MovieApiService, movieDao: MovieDao, context: Context) =
    MainRepositoryImpl(apiService, movieDao, context)