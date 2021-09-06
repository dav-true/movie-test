package com.example.movieapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.api.Constants
import com.example.movieapp.databinding.MovieItemBinding
import com.example.movieapp.dto.movie.Movie
import com.example.movieapp.interfaces.MovieClickListener

class MovieRecyclerViewAdapter(
    private val context: Context
) : PagingDataAdapter<Movie, MovieRecyclerViewAdapter.ViewHolder>(MovieComparator) {

    lateinit var clickListener: MovieClickListener

    class ViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            title.text =
                if (!getItem(position)?.title.isNullOrEmpty()) getItem(position)?.title else "No title"
            info.text =
                if (!getItem(position)?.overview.isNullOrEmpty()) getItem(position)?.overview else "No overview"
            Glide.with(context)
                .load(getItem(position)?.posterPath?.let { it -> Constants.imageLoadingUrl + it }
                    ?: context.getDrawable(R.drawable.no_poster))
                .into(poster)
            rating.text = getItem(position)?.voteAverage.toString()



            if (getItem(position)?.liked == true) {
                likeBtn.visibility = View.GONE
            } else {
                likeBtn.visibility = View.VISIBLE
                likeBtn.text = "Like"
                likeBtn.setOnClickListener {
                    getItem(position)?.liked = true
                    getItem(position)?.let { it1 -> clickListener.like(it1) }
                    notifyItemChanged(position, false)
                }
            }


            shareBtn.setOnClickListener {
                clickListener.share(getItem(position)!!)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        MovieItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    object MovieComparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem.movieId == newItem.movieId

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem == newItem
    }


}