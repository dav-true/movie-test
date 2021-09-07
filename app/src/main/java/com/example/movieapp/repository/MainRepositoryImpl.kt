package com.example.movieapp.repository

import android.content.Context
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.api.MovieApiService
import com.example.movieapp.dto.MovieResponse
import com.example.movieapp.dto.movie.Movie
import com.example.movieapp.dto.movie.MovieDao
import com.example.movieapp.paging.MoviesPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull

class MainRepositoryImpl(
    private val apiService: MovieApiService,
    private val movieDao: MovieDao,
    private val context: Context
) : MainRepository {


    override fun getMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviesPagingSource(service = apiService, movieDao = movieDao, context = context)
            }
        ).flow
    }

}