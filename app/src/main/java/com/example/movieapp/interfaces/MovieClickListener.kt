package com.example.movieapp.interfaces

import com.example.movieapp.dto.movie.Movie

interface MovieClickListener {
    fun like(movie: Movie)
    fun share(movie: Movie)
}