package com.example.movieapp.dto.movie

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies", indices = [Index(value = ["movieId"], unique = true)])
data class Movie(
    @PrimaryKey
    @SerializedName("id")
    val movieId: Int?,
    val title: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("vote_average")
    val voteAverage: Float,
    val overview: String?,
    var liked: Boolean = false
)