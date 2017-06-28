package com.kotlintest.services.navigation.manager

import android.os.Handler
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.Gravity

import com.kotlintest.R
import com.kotlintest.common.BaseActivity
import com.kotlintest.services.navigation.ScreenAnimType
import com.kotlintest.services.navigation.events.OnBackPressedEvent
import com.kotlintest.utils.PopupUtils

class ScreenNavigationBackManager(private val activity: BaseActivity) {

    private var animate: ScreenAnimType? = null
    private var doubleBackToExitPressedOnce = false

    init {
        this.animate = ScreenAnimType.FADE_TYPE
    }

    fun setScreenAnimType(animate: ScreenAnimType) {
        this.animate = animate
    }

    private fun navigateBack() {
        val fragmentManager = activity.supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            Log.i(TAG, "popping backstack")

            val backEntry = fragmentManager.getBackStackEntryAt(fragmentManager.backStackEntryCount - 1)
            val fragmentName = backEntry.name

            fragmentManager.popBackStackImmediate(fragmentName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        } else {
            Log.i(TAG, "nothing on backstack, calling finish")

            activity.hideKeyboard()

            if (activity.isTaskRoot) {
                if (doubleBackToExitPressedOnce) {
                    finishActivity()
                    return
                }

                doubleBackToExitPressedOnce = true
                PopupUtils.Companion.showCustomToast(activity, activity.getString(R.string.message_exit_from_app), Gravity.BOTTOM)
                Handler().postDelayed({ doubleBackToExitPressedOnce = false }, TIME_INTERVAL.toLong())
            } else {
                finishActivity()
            }
        }
    }

    private fun finishActivity() {
        activity.finish()

        when (animate) {
            ScreenAnimType.FADE_TYPE -> activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            ScreenAnimType.RIGHT_TO_LEFT_TYPE -> activity.overridePendingTransition(R.anim.right_to_left_in, R.anim.right_to_left_out)
            ScreenAnimType.LEFT_TO_RIGHT_TYPE -> activity.overridePendingTransition(R.anim.left_to_right_in, R.anim.left_to_right_out)
        }
    }

    fun onEvent(event: OnBackPressedEvent) {
        navigateBack()
    }

    companion object {
        private val TAG = ScreenNavigationBackManager::class.java.simpleName

        private val TIME_INTERVAL = 1600
    }
}
