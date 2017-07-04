package com.kotlintest

import android.os.Bundle
import android.os.Handler
import com.kotlintest.common.BaseActivity
import com.kotlintest.services.navigation.Screen
import com.kotlintest.services.navigation.ScreenType

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({ navigateToAllMovies() }, SPLASH_DELAY.toLong())
    }

    private fun navigateToAllMovies() {
        this.navigationController?.navigateTo(Screen.ALL_MOVIES, ScreenType.ACTIVITY)
    }
}
