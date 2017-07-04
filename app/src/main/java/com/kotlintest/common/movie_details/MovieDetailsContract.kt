package com.kotlintest.common.movie_details

import com.kotlintest.Movie

/**
 * Created by alex on 7/4/17.
 */
class MovieDetailsContract {

    interface View {

        fun progressDialogShow(show: Boolean)

        fun progressDialogFullShow(show: Boolean)

        fun showError(message: String)

        fun setupDetails(movie: Movie?)

    }

    interface Actions {

        fun setupDetails(movie: Movie?)

    }

}