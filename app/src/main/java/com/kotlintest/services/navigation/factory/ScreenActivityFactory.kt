package com.kotlintest.services.navigation.factory

import android.app.Activity
import android.content.Intent

import com.kotlintest.KotlinApp
import com.kotlintest.common.BaseActivity
import com.kotlintest.common.all_movies.ActivityAllMovies
import com.kotlintest.services.navigation.Screen


class ScreenActivityFactory {

    fun getActivityByType(type: Screen): Intent {
        val clazz = getActivityClassByType(type)
        return Intent(KotlinApp.kotlinApp!!.getAppContext(), clazz)
    }

    fun getActivityClassByType(type: Screen): Class<out Activity> {
        when (type) {
            Screen.ALL_MOVIES -> return ActivityAllMovies::class.java
            else -> return BaseActivity::class.java
        }
    }
}
