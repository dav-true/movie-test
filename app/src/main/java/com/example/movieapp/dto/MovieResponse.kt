package com.example.movieapp.dto


data class MovieResponse<T>(
    val dates: Dates,
    val page: Int,
    val results: T,
    val total_pages: Int,
    val total_results: Int
) {
    data class Dates(
        val minimum: String,
        val maximum: String
    )
}