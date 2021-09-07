package com.example.movieapp.dto.movie

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies", indices = [Index(value = ["primaryKey"], unique = true)])
data class Movie(
    @PrimaryKey(autoGenerate = true) val primaryKey: Int = 0,
    @SerializedName("id")
    val movieId: Int,
    val title: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("vote_average")
    val voteAverage: Float,
    val overview: String?,
    var liked: Boolean = false
)