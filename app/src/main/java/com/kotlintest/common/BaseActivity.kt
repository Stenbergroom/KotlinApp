package com.kotlintest.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.kotlintest.services.eventbus.EventBusCreator
import com.kotlintest.services.lifecycle.LifecycleManager
import com.kotlintest.services.navigation.NavigationController
import com.kotlintest.services.navigation.manager.ScreenNavigationBackManager
import com.kotlintest.services.navigation.manager.ScreenNavigationManager
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

/**
 * Created by alex on 12/27/16.
 */

abstract class BaseActivity : AppCompatActivity() {

    private var navigationBackManager: ScreenNavigationBackManager? = null
    protected val TAG = javaClass.simpleName
    protected var inputMethodManager: InputMethodManager? = null

    var navigationController: NavigationController? = null
    var navigationManager: ScreenNavigationManager? = null

    val eventBus = EventBusCreator.createDefault()
    val lifecycleManager = LifecycleManager(eventBus, TAG)

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, " onCreate()" + if (savedInstanceState != null) " recreating" else "")
        super.onCreate(savedInstanceState)
        inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        navigationBackManager = ScreenNavigationBackManager(this)
        navigationManager = ScreenNavigationManager(this, savedInstanceState)
        navigationController = navigationManager
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onStart() {
        Log.i(TAG, " onStart()")
        super.onStart()
        lifecycleManager.onStart()
        eventBus.register(navigationBackManager)
    }

    override fun onResume() {
        Log.i(TAG, " onResume()")
        super.onResume()
        lifecycleManager.onResume()
    }

    override fun onPostResume() {
        Log.i(TAG, " onPostResume()")
        super.onPostResume()
    }


    override fun onPause() {
        Log.i(TAG, " onPause()")
        super.onPause()
        lifecycleManager.onPause()
    }

    override fun onStop() {
        Log.i(TAG, " onStop()")
        lifecycleManager.onStop()
        eventBus.unregister(navigationBackManager)
        super.onStop()
    }

    override fun onDestroy() {
        Log.i(TAG, " onDestroy()")
        lifecycleManager.onDestroy()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        lifecycleManager.onSaveInstanceState(outState)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        lifecycleManager.onWindowFocusChanged(hasFocus)
    }

    override fun onBackPressed() {
        lifecycleManager.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
//                case Constants.REQUEST_FEEDBACK:
//                    eventBus.post(new UpdateScreenEvent());
//                    break;
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    val myFragmentManager: FragmentManager
        get() = supportFragmentManager

    fun hideKeyboard() {
        try {
            val windowToken = window.decorView.rootView.windowToken
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, 0)
        } catch (e: Exception) {
            Log.e(TAG, e.localizedMessage)
        }

    }

    fun freeMemory() {
        System.runFinalization()
        Runtime.getRuntime().gc()
        System.gc()
    }

    fun makeFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

}
