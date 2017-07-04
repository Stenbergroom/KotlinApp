package com.kotlintest.common.movie_details

import com.kotlintest.Movie
import com.kotlintest.common.BaseActivity
import de.greenrobot.event.EventBus

/**
 * Created by alex on 7/4/17.
 */
class MovieDetailsPresenter(
        var activity: BaseActivity?,
        var view: MovieDetailsContract.View?,
        var eventBus: EventBus?) : MovieDetailsContract.Actions {

    override fun setupDetails(movie: Movie?) {
        view?.setupDetails(movie)
    }
}