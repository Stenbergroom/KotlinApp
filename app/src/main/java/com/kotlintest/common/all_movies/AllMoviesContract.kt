package com.kotlintest.common.all_movies

import com.kotlintest.Movie

/**
 * Created by alex on 6/28/17.
 */
interface AllMoviesContract {

    interface View {

        fun progressDialogShow(show: Boolean)

        fun progressDialogFullShow(show: Boolean)

        fun showError(message: String)

        fun listItemClickAction(onItemClickListener: MoviesAdapter.OnItemClickListener)

        fun loadMoreMoviesListAction(onLoadMoreListener: MoviesAdapter.OnLoadMoreListener)

        fun setMoviesContent(dataList: MutableList<Movie>?)

        fun setMoreMoviesContent(dataList: MutableList<Movie>?)

        fun fetchMoviesAdapter(): MoviesAdapter?

    }

    interface Actions {

        fun clickMoviesListItem(position: Int)

        fun loadMoreMoviesList()

        fun setupList()

    }

}