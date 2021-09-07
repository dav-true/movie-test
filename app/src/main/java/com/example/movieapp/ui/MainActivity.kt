package com.example.movieapp.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.adapters.ViewPagerAdapter
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.dto.favorite_movie.FavoriteMovieDao
import com.example.movieapp.dto.movie.MovieDao
import com.example.movieapp.helpers.SharePreferencesHelper
import com.example.movieapp.helpers.viewBinding
import com.example.movieapp.room.AppDatabase
import com.example.movieapp.ui.auth.AuthActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.*
import kotlin.coroutines.coroutineContext

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)
    private val favoriteMovieDao: FavoriteMovieDao by inject()
    private val sharePreferencesHelper: SharePreferencesHelper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.contentMain.viewPager.offscreenPageLimit = 2

        Glide.with(this)
            .load(
                intent.extras?.getString("image_url")
                    ?.apply { binding.avatar.visibility = View.VISIBLE })
            .into(binding.avatar)

        binding.contentMain.viewPager.adapter = ViewPagerAdapter(this)
        binding.bottomNavView.setupWithViewPager2(binding.contentMain.viewPager)

        binding.exitBtn.setOnClickListener {
            finish()
//            CoroutineScope(Dispatchers.IO).launch {
//                favoriteMovieDao.deleteAllFavoriteMovies(userId = sharePreferencesHelper.getUserId())
//            }
            startActivity(Intent(this, AuthActivity::class.java))

        }

    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

}