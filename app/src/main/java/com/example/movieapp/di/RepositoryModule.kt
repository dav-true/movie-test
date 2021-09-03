package com.example.movieapp.di

import com.example.movieapp.api.MovieApiService
import com.example.movieapp.repository.MainRepository
import com.example.movieapp.repository.MainRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<MainRepository> { provideRepository(get()) }
}


fun provideRepository(apiService: MovieApiService) = MainRepositoryImpl(apiService)