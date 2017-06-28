package com.kotlintest.network

import com.kotlintest.MoviesResponse
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by alex on 6/26/17.
 */

class DefaultModel {

    private var kotlinApi: KotlinAPI? = null

    init {
        kotlinApi = RestService.createRestService()
    }

    /**
     * method for reload model, added when I faced the problem
     * connected with adding token to the requests
     */
    fun reloadModel() {
        kotlinApi = RestService.createRestService()
    }

    fun getMoviesList(apiKey: String, sortBy: String): Observable<MoviesResponse> {
        return kotlinApi!!.getMovies(sortBy, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getMoviesList(apiKey: String, sortBy: String, page: Int): Observable<MoviesResponse> {
        return kotlinApi!!.getMovies(sortBy, apiKey, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}
