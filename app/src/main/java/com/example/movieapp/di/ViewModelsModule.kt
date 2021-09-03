package com.example.movieapp.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.repository.MainRepository
import com.example.movieapp.viewmodels.MoviesViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.lang.ref.WeakReference


val viewModelModule = module {
    viewModel { provideMoviesViewModel(get(), WeakReference(androidApplication())) }
}


fun provideMoviesViewModel(repository: MainRepository, context: WeakReference<Context>) =
    MoviesViewModel(context, repository)