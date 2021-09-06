package com.example.movieapp.helpers

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences


class SharePreferencesHelper {
    companion object {
        fun getUserId(context: Context) =
            context.getSharedPreferences("user_info", MODE_PRIVATE).getInt("user_id", 1)


        fun putUserId(context: Context, id: Int) {
            val shared: SharedPreferences = context.getSharedPreferences("user_info", MODE_PRIVATE)
            shared.apply {
                edit().apply {
                    putInt("user_id", id)
                    apply()
                }

            }
        }
    }
}