package com.kotlintest.common.all_movies

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.RelativeLayout
import com.kotlintest.Movie
import com.kotlintest.R
import com.kotlintest.common.BaseActivity
import com.kotlintest.utils.PopupUtils
import de.greenrobot.event.EventBus

/**
 * Created by alex on 6/28/17.
 */
class AllMoviesView(
        var activity: BaseActivity?,
        var eventBus: EventBus?) : AllMoviesContract.View {

    var allMoviesRecycler: RecyclerView? = null
    var progressBarContainer: RelativeLayout? = null
    var moviesAdapter: MoviesAdapter? = null

    init {
        allMoviesRecycler = activity?.findViewById(R.id.allMoviesRecycler) as RecyclerView?
        progressBarContainer = activity?.findViewById(R.id.progressBarContainer) as RelativeLayout?
        moviesAdapter = MoviesAdapter(activity)
        allMoviesRecycler?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        allMoviesRecycler?.adapter = moviesAdapter
    }

    override fun progressDialogShow(show: Boolean) {
        if (show) {
            activity?.showProgressDialog()
        } else {
            activity?.dismissProgressDialog()
        }
    }

    override fun progressDialogFullShow(show: Boolean) {
        progressBarContainer?.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showError(message: String) {
        PopupUtils.Companion.showCustomToast(activity as BaseActivity, message, Gravity.BOTTOM)
    }

    override fun listItemClickAction(onItemClickListener: MoviesAdapter.OnItemClickListener) {
        moviesAdapter?.onItemClickListener = onItemClickListener
    }

    override fun loadMoreMoviesListAction(onLoadMoreListener: MoviesAdapter.OnLoadMoreListener) {
        moviesAdapter?.onLoadMoreListener = onLoadMoreListener
    }

    override fun setMoviesContent(dataList: MutableList<Movie>?) {
        moviesAdapter?.setContent(dataList)
    }
}