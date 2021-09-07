package com.example.movieapp.dto.favorite_movie

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface FavoriteMovieDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteMovie(favoriteMovie: FavoriteMovie)

    @Query("SELECT * from favorite_movies WHERE userId=:userId")
    suspend fun getFavoriteMovies(userId: String): List<FavoriteMovie>

    @Query("DELETE FROM favorite_movies WHERE movieId = :movieId AND userId = :userId ")
    suspend fun deleteFavoriteMovie(userId: String, movieId: Int)

    @Transaction
    suspend fun deleteFavoriteMovie(favoriteMovie: FavoriteMovie) {
        return deleteFavoriteMovie(favoriteMovie.userId, favoriteMovie.movie.movieId)
    }

    @Query("DELETE FROM favorite_movies WHERE userId = :userId")
    suspend fun deleteAllFavoriteMovies(userId: String)

}