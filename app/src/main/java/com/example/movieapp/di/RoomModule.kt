package com.example.movieapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.movieapp.dto.favorite_movie.FavoriteMovieDao
import com.example.movieapp.dto.movie.MovieDao
import com.example.movieapp.helpers.SharePreferencesHelper
import com.example.movieapp.room.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val roomModule = module {
    single { providesDatabase(androidApplication()) }
    single { provideMovieDao(get()) }
    single { provideFavoriteMovieDao(get()) }
    single { provideSharePreferences(androidApplication()) }
}


fun providesDatabase(application: Application): AppDatabase {
    return Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "movieapp.db"
    ).build()
}

fun provideMovieDao(database: AppDatabase) = database.movieDao


fun provideFavoriteMovieDao(database: AppDatabase) = database.favoriteMovieDao


fun provideSharePreferences(context: Context) = SharePreferencesHelper(context)