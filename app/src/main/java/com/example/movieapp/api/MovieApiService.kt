package com.example.movieapp.api

import com.example.movieapp.BuildConfig
import com.example.movieapp.dto.movie.Movie
import com.example.movieapp.dto.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET(Constants.ongoingMoviesUrl)
    suspend fun getMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.ApiKey
    ): MovieResponse<List<Movie>>
}