package com.example.movieapp.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.movieapp.dto.movie.Movie
import com.example.movieapp.dto.movie.MovieDao
import com.example.movieapp.repository.MainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class MoviesViewModel(
    private val context: WeakReference<Context>,
    private val repository: MainRepository,
    private val movieDao: MovieDao
) : ViewModel() {


    fun getAllMovies(): Flow<PagingData<Movie>> {
        return repository.getMovies()
    }




}