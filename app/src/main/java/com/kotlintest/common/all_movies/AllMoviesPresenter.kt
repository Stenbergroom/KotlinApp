package com.kotlintest.common.all_movies

import android.os.Bundle
import com.kotlintest.API_KEY
import com.kotlintest.KotlinApp
import com.kotlintest.MoviesResponse
import com.kotlintest.SORT_BY
import com.kotlintest.common.BaseActivity
import com.kotlintest.network.DefaultModel
import com.kotlintest.services.navigation.Screen
import com.kotlintest.services.navigation.ScreenType
import de.greenrobot.event.EventBus
import timber.log.Timber

/**
 * Created by alex on 6/28/17.
 */
class AllMoviesPresenter(
        var activity: BaseActivity?,
        var view: AllMoviesContract.View?,
        var eventBus: EventBus?) : AllMoviesContract.Actions {

    private var defaultModel: DefaultModel? = null
    private var nextPage: Int = -1

    init {
        defaultModel = KotlinApp.kotlinApp?.getModelService()?.getDefaultModel()
        setupList()
        setupActions()
    }

    private fun setupActions() {
        view?.listItemClickAction(onItemClickListener = object : MoviesAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                clickMoviesListItem(position)
            }

        })
        view?.loadMoreMoviesListAction(onLoadMoreListener = object : MoviesAdapter.OnLoadMoreListener {
            override fun onLoadMore() {
                loadMoreMoviesList()
            }
        })
    }

    override fun clickMoviesListItem(position: Int) {
        val bundle = Bundle()
        bundle.putSerializable("movie", view?.fetchMoviesAdapter()?.moviesList?.get(position))
        activity?.navigationController?.navigateTo(Screen.MOVIE_DETAILS, ScreenType.ACTIVITY, bundle)
    }

    override fun loadMoreMoviesList() {
        defaultModel?.getMoviesList(API_KEY, SORT_BY, ++nextPage)?.subscribe(
                { onNext: MoviesResponse? ->
                    nextPage = onNext?.page as Int
                    view?.setMoreMoviesContent(onNext.results)
                },
                { error ->
                    view?.progressDialogFullShow(false)
                    Timber.d("error message - ${error.localizedMessage}")
                },
                { ->
                    Timber.d("onCompleted()")
                })
    }

    override fun setupList() {
        view?.progressDialogFullShow(true)
        defaultModel?.getMoviesList(API_KEY, SORT_BY)?.subscribe(
                { onNext: MoviesResponse? ->
                    nextPage = onNext?.page as Int
                    view?.setMoviesContent(onNext.results)
                    view?.progressDialogFullShow(false)
                },
                { error ->
                    view?.progressDialogFullShow(false)
                    Timber.d("error message - ${error.localizedMessage}")
                },
                { ->
                    Timber.d("onCompleted()")
                })
    }
}