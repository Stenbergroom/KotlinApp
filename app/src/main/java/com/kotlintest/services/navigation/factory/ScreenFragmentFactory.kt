package com.kotlintest.services.navigation.factory


import android.content.Context
import android.support.v4.app.Fragment

import com.kotlintest.KotlinApp
import com.kotlintest.common.BaseFragment
import com.kotlintest.services.navigation.Screen


class ScreenFragmentFactory {

    protected var context: Context? = null

    fun getFragmentByType(type: Screen): Fragment {
        val clazz = getFragmentClassByType(type)
        return Fragment.instantiate(KotlinApp.kotlinApp!!.getAppContext(), clazz.name)
    }

    fun getFragmentClassByType(type: Screen): Class<out Fragment> {
        when (type) {
            Screen.BASE_FRAGMENT -> return BaseFragment::class.java
            else -> return BaseFragment::class.java
        }
    }
}
