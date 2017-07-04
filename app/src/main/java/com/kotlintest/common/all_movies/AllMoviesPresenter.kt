package com.kotlintest.common.all_movies

import com.kotlintest.API_KEY
import com.kotlintest.KotlinApp
import com.kotlintest.MoviesResponse
import com.kotlintest.SORT_BY
import com.kotlintest.common.BaseActivity
import com.kotlintest.network.DefaultModel
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
        Timber.d("click")
    }

    override fun loadMoreMoviesList() {
        Timber.d("loadMore")
    }

    override fun setupList() {
        view?.progressDialogFullShow(true)
        defaultModel?.getMoviesList(API_KEY, SORT_BY)?.subscribe(
                { onNext: MoviesResponse? ->
                    view?.setMoviesContent(onNext?.results)
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