package com.kotlintest.services.lifecycle

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log

import com.kotlintest.services.lifecycle.events.ActivityInvisibleEvent
import com.kotlintest.services.lifecycle.events.ActivityVisibleEvent
import com.kotlintest.services.lifecycle.events.OnConfigurationChangedEvent
import com.kotlintest.services.lifecycle.events.OnDestroyEvent
import com.kotlintest.services.lifecycle.events.OnDetachEvent
import com.kotlintest.services.lifecycle.events.OnPauseEvent
import com.kotlintest.services.lifecycle.events.OnResumeEvent
import com.kotlintest.services.lifecycle.events.OnSaveInstanceStateEvent
import com.kotlintest.services.lifecycle.events.OnStartEvent
import com.kotlintest.services.lifecycle.events.OnStopEvent
import com.kotlintest.services.lifecycle.events.OnWindowFocusChangedEvent
import com.kotlintest.services.navigation.events.OnBackPressedEvent

import de.greenrobot.event.EventBus

class LifecycleManager @JvmOverloads constructor(val eventBus: EventBus, private val TAG: String? = null) {
    private val log: Boolean

    /**
     * For Activities only
     */
    var isVisible: Boolean = false
        private set

    init {
        log = TAG != null
    }

    fun onStart() {
        eventBus.post(OnStartEvent())
    }

    fun onStop() {
        isVisible = false
        eventBus.post(ActivityInvisibleEvent())
        eventBus.post(OnStopEvent())
    }

    fun onDetach() {
        eventBus.post(OnDetachEvent())
    }

    fun onResume() {
        eventBus.post(OnResumeEvent())
    }

    fun onPause() {
        eventBus.post(OnPauseEvent())
    }

    fun onSaveInstanceState(outState: Bundle) {
        eventBus.post(OnSaveInstanceStateEvent(outState))
    }

    fun onDestroy() {
        eventBus.post(OnDestroyEvent())
    }

    fun onBackPressed() {
        eventBus.post(OnBackPressedEvent())
    }

    fun onWindowFocusChanged(hasFocus: Boolean) { //is this possible that Activity doesn't lose focus after onStop()..? There was a bug, ActivityVisibleEvent didn't arrive...
        eventBus.post(OnWindowFocusChangedEvent(hasFocus))
        if (hasFocus && !isVisible) {
            isVisible = true
            eventBus.post(ActivityVisibleEvent())
        }
        //endregion
    }

    fun onConfigurationChanged(newConfig: Configuration) {
        //see ScreenOrientationManager if you want to distinct landscape/portrait events and notify properly
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (log) Log.i(TAG, "onConfigurationChanged(landscape)")
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (log) Log.i(TAG, "onConfigurationChanged(portrait)")
        }
        eventBus.post(OnConfigurationChangedEvent(newConfig))
    }
}