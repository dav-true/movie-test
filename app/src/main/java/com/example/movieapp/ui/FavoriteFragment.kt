package com.example.movieapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.adapters.FavoriteMoviesRecyclerViewAdapter
import com.example.movieapp.databinding.FragmentFavoriteBinding
import com.example.movieapp.dto.movie.Movie
import com.example.movieapp.helpers.viewBinding
import com.example.movieapp.interfaces.FavoriteMovieClickListener
import com.example.movieapp.interfaces.MovieClickListener
import com.example.movieapp.viewmodels.MoviesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class FavoriteFragment : Fragment(R.layout.fragment_favorite), FavoriteMovieClickListener {

    private val binding: FragmentFavoriteBinding by viewBinding(FragmentFavoriteBinding::bind)
    private val viewModel by viewModel<MoviesViewModel>()
    private val favoriteMoviesAdapter: FavoriteMoviesRecyclerViewAdapter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favoriteMoviesAdapter.apply { clickListener = this@FavoriteFragment }
        }
        getFavoriteMovies()
    }

    private fun getFavoriteMovies() {
        lifecycleScope.launch {
            favoriteMoviesAdapter.apply {
                favoriteMoviesList.clear()
                favoriteMoviesList.addAll(viewModel.getFavoriteMovies())
                notifyDataSetChanged()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getFavoriteMovies()
    }

    override fun share(movie: Movie) {
    }

    override fun remove(movie: Movie) {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.deleteFavoriteMovie(movie)
        }
    }


}