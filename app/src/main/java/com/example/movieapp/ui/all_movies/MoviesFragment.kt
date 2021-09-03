package com.example.movieapp.ui.all_movies

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.example.movieapp.R
import com.example.movieapp.adapters.MovieRecyclerViewAdapter
import com.example.movieapp.databinding.FragmentMoviesBinding
import com.example.movieapp.helpers.GestureListener
import com.example.movieapp.helpers.viewBinding
import com.example.movieapp.viewmodels.MoviesViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.viewmodel.ext.android.viewModel


class MoviesFragment : Fragment(R.layout.fragment_movies) {

    private val binding: FragmentMoviesBinding by viewBinding(FragmentMoviesBinding::bind)
    private val viewModel by viewModel<MoviesViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gestureDetector = GestureDetector(activity, GestureListener(binding.recyclerView))
        val movieAdapter = MovieRecyclerViewAdapter(requireContext())
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = movieAdapter
            addOnItemTouchListener(rvTouchListener(gestureDetector))
        }

        getAllMovies(movieAdapter)


        binding.loading.setOnRefreshListener {
            getAllMovies(movieAdapter)
            binding.loading.isRefreshing = false
        }
    }


    private fun getAllMovies(movieAdapter: MovieRecyclerViewAdapter) {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.getAllMovies().collectLatest { pagingData ->
                movieAdapter.submitData(pagingData)
            }
        }
    }




    private fun rvTouchListener(gestureDetector: GestureDetector) = object : OnItemTouchListener {
        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            gestureDetector.onTouchEvent(e);
            return false;

        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        }

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        }
    }

}