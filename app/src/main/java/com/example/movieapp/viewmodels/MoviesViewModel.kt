package com.example.movieapp.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.room.Update
import com.example.movieapp.dto.favorite_movie.FavoriteMovie
import com.example.movieapp.dto.favorite_movie.FavoriteMovieDao
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
    private val movieDao: MovieDao,
    private val favoriteMovieDao: FavoriteMovieDao
) : ViewModel() {


    fun getAllMovies(): Flow<PagingData<Movie>> {
        return repository.getMovies()
    }

    suspend fun addFavoriteMovie(movie: Movie) {
        favoriteMovieDao.addFavoriteMovie(FavoriteMovie(movie = movie))
    }

    suspend fun getFavoriteMovies() =
        favoriteMovieDao.getFavoriteMovies()

    suspend fun deleteFavoriteMovie(movie: Movie) {
        favoriteMovieDao.deleteFavoriteMovie(FavoriteMovie(movie = movie))
    }


}