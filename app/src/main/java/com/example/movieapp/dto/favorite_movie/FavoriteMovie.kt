package com.example.movieapp.dto.favorite_movie

import androidx.room.Embedded
import androidx.room.Entity
import com.example.movieapp.dto.movie.Movie

@Entity(tableName = "favorite_movies")
data class FavoriteMovie(
    val userId: Int,
    val movie: Movie
)