package com.kotlintest

import android.os.Bundle
import com.kotlintest.common.BaseActivity
import com.kotlintest.network.DefaultModel
import timber.log.Timber

class HomeActivity : BaseActivity() {

    private var defaultModel: DefaultModel? = KotlinApp.kotlinApp?.getModelService()?.getDefaultModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        defaultModel?.getMoviesList(API_KEY, SORT_BY)?.subscribe(
                { onNext: MoviesResponse? -> Timber.d("result - ${onNext?.results?.size}") },
                { error -> Timber.d("error message - ${error.localizedMessage}") },
                { -> Timber.d("onCompleted()") })
        KotlinApp.Companion.kotlinApp?.runOnUiThread(Runnable { Timber.d("ok===============") })
    }
}
