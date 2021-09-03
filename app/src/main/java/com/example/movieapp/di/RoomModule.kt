package com.example.movieapp.di

import android.app.Application
import androidx.room.Room
import com.example.movieapp.dto.favorite_movie.FavoriteMovieDao
import com.example.movieapp.dto.movie.MovieDao
import com.example.movieapp.room.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val roomModule = module {
    single { providesDatabase(androidApplication()) }
    single { provideMovieDao(get()) }
    single { provideFavoriteMovieDao(get()) }
}


fun providesDatabase(application: Application): AppDatabase {
    return Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "instagram.db"
    ).build()
}

fun provideMovieDao(database: AppDatabase): MovieDao {
    return database.movieDao
}

fun provideFavoriteMovieDao(database: AppDatabase): FavoriteMovieDao {
    return database.favoriteMovieDao
}