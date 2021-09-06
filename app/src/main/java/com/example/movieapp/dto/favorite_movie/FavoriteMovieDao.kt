package com.example.movieapp.dto.favorite_movie

import androidx.room.*
import com.example.movieapp.helpers.SharePreferencesHelper

@Dao
interface FavoriteMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteMovie(favoriteMovie: FavoriteMovie)

    @Query("SELECT * from favorite_movies")
    suspend fun getFavoriteMovies(): List<FavoriteMovie>


    @Delete
    suspend fun deleteFavoriteMovie(favoriteMovie: FavoriteMovie)
}