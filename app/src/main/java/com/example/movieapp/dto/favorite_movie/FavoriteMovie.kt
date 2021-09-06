package com.example.movieapp.dto.favorite_movie

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.movieapp.dto.movie.Movie

@Entity(tableName = "favorite_movies", primaryKeys = ["movieId"])
data class FavoriteMovie(
    @Embedded val movie: Movie
)