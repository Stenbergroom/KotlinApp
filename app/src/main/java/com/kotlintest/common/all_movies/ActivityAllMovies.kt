package com.kotlintest.common.all_movies

import android.os.Bundle
import com.kotlintest.R
import com.kotlintest.common.BaseActivity

/**
 * Created by alex on 6/27/17.
 */

class ActivityAllMovies : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_movies)
    }
}


