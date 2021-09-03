package com.example.movieapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapp.dto.favorite_movie.FavoriteMovie
import com.example.movieapp.dto.favorite_movie.FavoriteMovieDao
import com.example.movieapp.dto.movie.Movie
import com.example.movieapp.dto.movie.MovieDao

@Database(
    entities = [Movie::class, FavoriteMovie::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){
    abstract val movieDao: MovieDao
    abstract val favoriteMovieDao: FavoriteMovieDao
}