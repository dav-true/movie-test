package com.example.movieapp.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.api.MovieApiService
import com.example.movieapp.dto.movie.Movie
import com.example.movieapp.dto.movie.MovieDao
import retrofit2.HttpException
import java.io.IOException

class MoviesPagingSource(
    private val service: MovieApiService,
    private val movieDao: MovieDao
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val pageIndex = params.key ?: 1

        return try {
            val response = service.getMovies(
                page = pageIndex
            )

            if (pageIndex == 1) {
                movieDao.setMovies(response.results)
            }

            val movies = response.results
            val nextPageNumber: Int? = if (response.page != response.total_pages) {
                response.page + 1
            } else {
                null
            }

            Log.d("pagining", "Previous page: $pageIndex; Next page: $nextPageNumber")

            LoadResult.Page(
                data = movies,
                prevKey = if (pageIndex == 1) null else pageIndex - 1,
                nextKey = nextPageNumber
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override val keyReuseSupported: Boolean
        get() = true
}