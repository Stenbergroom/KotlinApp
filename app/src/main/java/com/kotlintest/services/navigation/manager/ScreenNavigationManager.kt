package com.kotlintest.services.navigation.manager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.kotlintest.R
import com.kotlintest.common.BaseActivity
import com.kotlintest.services.navigation.NavigationController
import com.kotlintest.services.navigation.Screen
import com.kotlintest.services.navigation.ScreenAnimType
import com.kotlintest.services.navigation.ScreenType
import com.kotlintest.services.navigation.factory.ScreenActivityFactory
import com.kotlintest.services.navigation.factory.ScreenFragmentFactory

class ScreenNavigationManager(private val activity: BaseActivity, private val savedInstanceState: Bundle?) : NavigationController {

    private val activityFactory: ScreenActivityFactory
    private val fragmentFactory: ScreenFragmentFactory

    var activeScreen: Screen? = Screen.NONE

    init {
        activityFactory = ScreenActivityFactory()
        fragmentFactory = ScreenFragmentFactory()
    }

    fun onSaveInstanceState(outState: Bundle) {
        if (activeScreen != null) {
            outState.putSerializable(EXTRA_ACTIVE_SCREEN, activeScreen)
        }
    }

    override fun navigateTo(screen: Screen, type: ScreenType) {
        navigateTo(screen, type, null)
    }

    override fun navigateTo(screen: Screen, type: ScreenType, bundle: Bundle?) {
        when (type) {
            ScreenType.ACTIVITY -> navigateToActivity(screen, bundle)
            ScreenType.FRAGMENT -> navigateToFragment(screen, bundle)
            ScreenType.OUTSIDE -> navigateToOutside(screen, bundle)
        }
    }

    private fun navigateToActivity(screen: Screen, bundle: Bundle?) {
        when (screen) {
            Screen.ALL_MOVIES -> navigateToAllMovies(bundle)
            Screen.MOVIE_DETAILS -> navigateToMovieDetails(bundle)
        }
    }

    private fun navigateToFragment(screen: Screen, bundle: Bundle?) {
        when (screen) {
            Screen.BASE_FRAGMENT -> navigateToBaseFragment(bundle)
        }
    }

    private fun navigateToOutside(screen: Screen, bundle: Bundle?) {
        /*switch (screen) {
            case FACEBOOK:
                navigateToFacebook(bundle);
                break;
            case TWITTER:
                navigateToTwitter(bundle);
                break;
            case YOUTUBE:
                navigateToYoutube(bundle);
                break;
            case INSTAGRAM:
                navigateToInstagram(bundle);
                break;
            case TERMS:
                navigateToTerms(bundle);
                break;
            case PRIVACY:
                navigateToPrivacy(bundle);
                break;
        }*/
    }

    // Activity

    private fun navigateToAllMovies(bundle: Bundle?) {
        Log.d(TAG, "start AllMoviesActivity")
        activeScreen = Screen.ALL_MOVIES
        switchActivityScreen(Screen.ALL_MOVIES, bundle, ScreenAnimType.FADE_TYPE, true)
        activity.hideKeyboard()
        activity.finish()
    }

    private fun navigateToMovieDetails(bundle: Bundle?) {
        Log.d(TAG, "start MovieDetails")
        activeScreen = Screen.MOVIE_DETAILS
        switchActivityScreen(Screen.MOVIE_DETAILS, bundle, ScreenAnimType.FADE_TYPE, false)
        activity.hideKeyboard()
    }

    // Fragments

    private fun navigateToBaseFragment(bundle: Bundle?) {
        Log.d(TAG, "start BaseFragment")
        activeScreen = Screen.BASE_FRAGMENT
        switchFragmentScreen(Screen.BASE_FRAGMENT, bundle, false, false)
    }

    private fun switchActivityScreen(type: Screen, bundle: Bundle?, animate: ScreenAnimType, makeActivityRoot: Boolean) {
        val intent = activityFactory.getActivityByType(type)
        if (makeActivityRoot) {
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        if (bundle != null && !bundle.isEmpty) {
            intent.putExtras(bundle)
        }

        activity.startActivity(intent)

        when (animate) {
            ScreenAnimType.FADE_TYPE -> activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            ScreenAnimType.RIGHT_TO_LEFT_TYPE -> activity.overridePendingTransition(R.anim.right_to_left_in, R.anim.right_to_left_out)
            ScreenAnimType.LEFT_TO_RIGHT_TYPE -> activity.overridePendingTransition(R.anim.left_to_right_in, R.anim.left_to_right_out)
        }
    }

    private fun switchFragmentScreen(type: Screen, bundle: Bundle?, animate: Boolean, addToBackStack: Boolean) {
        if (isSameFragmentAlreadyPlaced(type)) {
            return
        }

        val fragmentManager = activity.supportFragmentManager
        val tran = fragmentManager.beginTransaction()
        if (animate) {
            tran.setCustomAnimations(R.anim.popup_in, R.anim.popup_out)
        }

        val fragment = fragmentFactory.getFragmentByType(type)
        if (bundle != null && !bundle.isEmpty) {
            fragment.arguments = bundle
        }
        if (addToBackStack) {
            if (animate) {
                tran.setCustomAnimations(R.anim.right_to_left_in, R.anim.right_to_left_out, R.anim.left_to_right_in, R.anim.left_to_right_out)
            }
            tran.replace(R.id.container, fragment, fragment.javaClass.simpleName)
            tran.addToBackStack(fragment.javaClass.simpleName)
        } else {
            if (animate) {
                tran.setCustomAnimations(R.anim.popup_in, R.anim.popup_out)
            }
            tran.replace(R.id.container, fragment)
        }
        tran.commit()
    }

    private fun isSameFragmentAlreadyPlaced(type: Screen): Boolean {
        val existing = activity.supportFragmentManager.findFragmentById(R.id.container)
        if (existing != null) {
            val requested = fragmentFactory.getFragmentClassByType(type)
            if (existing.javaClass == requested) {
                return true
            }
        }
        return false
    }

    private fun setInitialScreen(savedScreen: Screen) {
        switchFragmentScreen(savedScreen, null, false, true)
        activeScreen = savedScreen
    }

    companion object {
        private val TAG = ScreenNavigationManager::class.java.simpleName

        private val EXTRA_ACTIVE_SCREEN = "ScreenNavigationManager.activeScreen"
    }
}
