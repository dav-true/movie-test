package com.example.movieapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.api.Constants
import com.example.movieapp.databinding.MovieItemBinding
import com.example.movieapp.dto.favorite_movie.FavoriteMovie
import com.example.movieapp.interfaces.FavoriteMovieClickListener
import com.example.movieapp.interfaces.MovieClickListener

class FavoriteMoviesRecyclerViewAdapter(
    private val context: Context
) : RecyclerView.Adapter<FavoriteMoviesRecyclerViewAdapter.ViewHolder>() {

    val favoriteMoviesList: MutableList<FavoriteMovie> = mutableListOf()
    lateinit var clickListener: FavoriteMovieClickListener

    class ViewHolder(val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        MovieItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.apply {
            likeBtn.text = "Remove"

            title.text =
                if (!favoriteMoviesList[position].movie.title.isNullOrEmpty()) favoriteMoviesList[position].movie.title else "No title"
            info.text =
                if (!favoriteMoviesList[position].movie.overview.isNullOrEmpty()) favoriteMoviesList[position].movie.overview else "No overview"
            Glide.with(context)
                .load(favoriteMoviesList[position].movie.posterPath?.let { it -> Constants.imageLoadingUrl + it }
                    ?: context.getDrawable(R.drawable.no_poster))
                .into(poster)
            rating.text = favoriteMoviesList[position].movie.voteAverage.toString()

            likeBtn.setOnClickListener {
                favoriteMoviesList[holder.absoluteAdapterPosition].movie.let {
                    clickListener.remove(it)
                }
                favoriteMoviesList.removeAt(holder.absoluteAdapterPosition)
                notifyItemRemoved(holder.absoluteAdapterPosition)
            }

            shareBtn.setOnClickListener {
                clickListener.share(favoriteMoviesList[holder.absoluteAdapterPosition].movie)
            }
        }

    }

    override fun getItemCount() = favoriteMoviesList.size
}