package com.example.movieapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.api.MovieApiService
import com.example.movieapp.dto.MovieResponse
import com.example.movieapp.dto.movie.Movie
import com.example.movieapp.paging.MoviesPagingSource
import kotlinx.coroutines.flow.Flow

class MainRepositoryImpl(private val apiService: MovieApiService) : MainRepository {
    override fun getMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviesPagingSource(service = apiService)
            }
        ).flow
    }

}