package com.example.movieapp.dto.favorite_movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteMovie(favoriteMovie: FavoriteMovie)

    @Query("SELECT * from favorite_movies")
    suspend fun getFavoriteMovies(): List<FavoriteMovie>
}