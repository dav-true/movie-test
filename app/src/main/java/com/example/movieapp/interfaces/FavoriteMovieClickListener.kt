package com.example.movieapp.interfaces

import com.example.movieapp.dto.movie.Movie

interface FavoriteMovieClickListener {
    fun remove(movie: Movie)
    fun share(movie: Movie)
}