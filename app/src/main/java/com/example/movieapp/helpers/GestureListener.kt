package com.example.movieapp.helpers

import android.util.Log
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs


class GestureListener(private val recyclerView: RecyclerView) : SimpleOnGestureListener() {
    private val Y_BUFFER = 10


    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        recyclerView.parent.requestDisallowInterceptTouchEvent(false)

        return super.onSingleTapUp(e)
    }

    override fun onScroll(
        e1: MotionEvent,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        if (Math.abs(distanceY) > Y_BUFFER) {
            recyclerView.parent.requestDisallowInterceptTouchEvent(true)
        }
        return super.onScroll(e1, e2, distanceX, distanceY)
    }

}