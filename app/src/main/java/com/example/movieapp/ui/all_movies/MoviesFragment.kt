package com.example.movieapp.ui.all_movies

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.filter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.example.movieapp.R
import com.example.movieapp.adapters.LoadingStateAdapter
import com.example.movieapp.adapters.MovieRecyclerViewAdapter
import com.example.movieapp.databinding.FragmentMoviesBinding
import com.example.movieapp.dto.favorite_movie.FavoriteMovie
import com.example.movieapp.dto.movie.Movie
import com.example.movieapp.helpers.GestureListener
import com.example.movieapp.helpers.viewBinding
import com.example.movieapp.helpers.withLoadStateAdapters
import com.example.movieapp.interfaces.MovieClickListener
import com.example.movieapp.viewmodels.MoviesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class MoviesFragment : Fragment(R.layout.fragment_movies), MovieClickListener {

    private val binding: FragmentMoviesBinding by viewBinding(FragmentMoviesBinding::bind)
    private val viewModel by viewModel<MoviesViewModel>()
    private val movieAdapter: MovieRecyclerViewAdapter by inject()
    private val favoriteMovies: MutableList<FavoriteMovie> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gestureDetector = GestureDetector(activity, GestureListener(binding.recyclerView))

        movieAdapter.clickListener = this@MoviesFragment
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = with(movieAdapter) {
                withLoadStateHeaderAndFooter(
                    header = LoadingStateAdapter(movieAdapter),
                    footer = LoadingStateAdapter(movieAdapter)
                )
            }
            addOnItemTouchListener(rvTouchListener(gestureDetector))
        }


//        with(movieAdapter) {
//            clickListener = this@MoviesFragment
//            binding.recyclerView.addOnItemTouchListener(rvTouchListener(gestureDetector))
//            binding.recyclerView.layoutManager = LinearLayoutManager(context)
//            binding.recyclerView.adapter = withLoadStateHeaderAndFooter(
//                header = LoadingStateAdapter(this),
//                footer = LoadingStateAdapter(this)
//            )
//        }

        binding.loading.setOnRefreshListener {
            initLoad()
        }
        initLoad()


    }

    private fun initLoad() {
        getFavoriteMovies().invokeOnCompletion {
            getAllMovies()
        }
    }

    private fun getAllMovies() = viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {

        viewModel.getAllMovies()
            .map {
                it.filter { movie ->
                    favoriteMovies.forEach { favoriteMovie ->
                        if (movie.movieId == favoriteMovie.movie.movieId) movie.liked = true
                    }
                    true
                }
            }
            .onStart {
                if (binding.loading.isRefreshing) {
                    binding.loading.isRefreshing = false
                }
            }
            .collectLatest { pagingData ->

                movieAdapter.submitData(pagingData)
            }
    }

    private fun getFavoriteMovies() = viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
        favoriteMovies.apply {
            clear()
            addAll(viewModel.getFavoriteMovies())
        }
    }


    override fun like(movie: Movie) {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            viewModel.addFavoriteMovie(movie)
        }
    }

    override fun share(movie: Movie) {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "MovieApp")
            val shareMessage = "Movie: ${movie.title}"
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, "Choose:"))
        } catch (e: Exception) {
            Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }


    private fun rvTouchListener(gestureDetector: GestureDetector) =
        object : OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                gestureDetector.onTouchEvent(e);
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            }
        }

    override fun onResume() {
        super.onResume()
        removeLikedFromMovies()
    }

    private fun removeLikedFromMovies() {
        getFavoriteMovies().invokeOnCompletion {
            lifecycleScope.launch(Dispatchers.IO) {
                movieAdapter.snapshot().toMutableList().forEach { movie ->
                    movie?.liked = favoriteMovies.any { favoriteMovie ->
                        movie?.movieId == favoriteMovie.movie.movieId
                    }
                }
                CoroutineScope(Dispatchers.Main).launch {
                    movieAdapter.notifyDataSetChanged()
                }
            }
        }

    }
}