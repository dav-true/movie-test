package com.example.movieapp.dto.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    suspend fun getMovies(): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setMovies(list: List<Movie>)

    @Query("DELETE FROM movies")
    suspend fun deleteMovies()


}