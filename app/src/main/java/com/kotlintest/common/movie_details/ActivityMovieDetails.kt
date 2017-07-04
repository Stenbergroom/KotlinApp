package com.kotlintest.common.movie_details

import android.os.Bundle
import com.kotlintest.Movie
import com.kotlintest.R
import com.kotlintest.common.BaseActivity

/**
 * Created by alex on 7/4/17.
 */
class ActivityMovieDetails : BaseActivity() {

    private var activity: BaseActivity? = null
    private var movieDetailsView: MovieDetailsView? = null
    private var movieDetailsPresenter: MovieDetailsPresenter? = null
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        activity = this
        eventBus = (activity as BaseActivity).eventBus
        movieDetailsView = MovieDetailsView(activity, eventBus)
        movieDetailsPresenter = MovieDetailsPresenter(activity as? BaseActivity, movieDetailsView, eventBus)

        movie = intent.getSerializableExtra("movie") as Movie
        movieDetailsPresenter?.setupDetails(movie)
    }
}