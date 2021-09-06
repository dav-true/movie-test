package com.example.movieapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.adapters.ViewPagerAdapter
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.helpers.viewBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.contentMain.viewPager.offscreenPageLimit = 3

        binding.contentMain.viewPager.adapter = ViewPagerAdapter(this)
        binding.bottomNavView.setupWithViewPager2(binding.contentMain.viewPager)

    }

}