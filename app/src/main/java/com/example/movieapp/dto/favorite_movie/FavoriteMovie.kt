package com.example.movieapp.dto.favorite_movie

import androidx.room.*
import com.example.movieapp.dto.movie.Movie

@Entity(tableName = "favorite_movies")
data class FavoriteMovie(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    @Embedded val movie: Movie
)