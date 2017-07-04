package com.kotlintest.common.movie_details

import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.kotlintest.IMAGE_ENDPOINT
import com.kotlintest.Movie
import com.kotlintest.R
import com.kotlintest.common.BaseActivity
import com.kotlintest.utils.PopupUtils
import com.squareup.picasso.Picasso
import de.greenrobot.event.EventBus

/**
 * Created by alex on 7/4/17.
 */
class MovieDetailsView(
        var activity: BaseActivity?,
        var eventBus: EventBus?) : MovieDetailsContract.View {

    var progressBarContainer: RelativeLayout? = null
    var imgMovie: ImageView
    var tvTitle: TextView
    var tvLanguage: TextView
    var tvReleaseDate: TextView
    var tvOverview: TextView

    init {
        progressBarContainer = activity?.findViewById(R.id.progressBarContainer) as RelativeLayout?
        imgMovie = activity?.findViewById(R.id.imgMovie) as ImageView
        tvTitle = activity?.findViewById(R.id.tvTitle) as TextView
        tvLanguage = activity?.findViewById(R.id.tvLanguage) as TextView
        tvReleaseDate = activity?.findViewById(R.id.tvReleaseDate) as TextView
        tvOverview = activity?.findViewById(R.id.tvOverview) as TextView
    }

    override fun setupDetails(movie: Movie?) {
        var originalLanguage: String = activity?.getString(R.string.original_language) + " - " + movie?.original_language
        var releaseDate = activity?.getString(R.string.release_date) + " - " + movie?.release_date
        Picasso.with(activity).load(IMAGE_ENDPOINT + movie?.poster_path).fit().centerCrop().into(imgMovie)
        tvTitle.text = movie?.title
        tvLanguage.text = originalLanguage
        tvReleaseDate.text = releaseDate
        tvOverview.text = movie?.overview
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
}