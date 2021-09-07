package com.example.movieapp.helpers

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences


class SharePreferencesHelper(
    val context: Context
) {

    fun getUserId() =
        context.getSharedPreferences("user_info", MODE_PRIVATE).getString("user_id", "1")!!


    fun putUserId(userId: String) {
        val shared: SharedPreferences = context.getSharedPreferences("user_info", MODE_PRIVATE)
        shared.apply {
            edit().apply {
                putString("user_id", userId)
                apply()
            }

        }
    }

}