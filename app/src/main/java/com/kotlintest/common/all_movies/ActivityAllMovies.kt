package com.kotlintest.common.all_movies

import android.os.Bundle
import com.kotlintest.R
import com.kotlintest.common.BaseActivity

/**
 * Created by alex on 6/27/17.
 */

class ActivityAllMovies : BaseActivity() {

    private var activity: BaseActivity? = null
    private var allMoviesView: AllMoviesView? = null
    private var allMoviesPresenter: AllMoviesPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_movies)
        activity = this
        eventBus = (activity as BaseActivity).eventBus
        allMoviesView = AllMoviesView(activity, eventBus)
        allMoviesPresenter = AllMoviesPresenter(activity as? BaseActivity, allMoviesView, eventBus)
    }

    override fun onResume() {
        super.onResume()
        allMoviesPresenter?.setupList()
    }
}


