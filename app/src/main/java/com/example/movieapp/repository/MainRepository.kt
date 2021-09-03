package com.example.movieapp.repository

import androidx.paging.PagingData
import com.example.movieapp.dto.MovieResponse
import com.example.movieapp.dto.movie.Movie
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getMovies(): Flow<PagingData<Movie>>
}