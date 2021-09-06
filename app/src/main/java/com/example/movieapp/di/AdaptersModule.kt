package com.example.movieapp.di

import android.content.Context
import com.example.movieapp.adapters.FavoriteMoviesRecyclerViewAdapter
import com.example.movieapp.adapters.MovieRecyclerViewAdapter
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val adaptersModule = module {
    factory { provideMoviesRecyclerViewAdapter(androidApplication()) }
    factory { provideFavoriteMoviesRecyclerViewAdapter(androidApplication()) }
}

fun provideMoviesRecyclerViewAdapter(context: Context) =
    MovieRecyclerViewAdapter(context)

fun provideFavoriteMoviesRecyclerViewAdapter(context: Context) =
    FavoriteMoviesRecyclerViewAdapter(context)
