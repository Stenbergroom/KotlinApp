package com.kotlintest

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import timber.log.Timber

/**
 * Created by alex on 6/26/17.
 */

class KotlinApp : Application() {

    companion object {
        var kotlinApp: KotlinApp? = null
    }

    private var kotlinContext: Context? = null
    private var kotlinHandler: Handler? = null
    private var modelService: ModelService? = null

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        kotlinApp = this
        kotlinContext = getApplicationContext()
        kotlinHandler = Handler(Looper.getMainLooper())
    }

    fun getInstance(): KotlinApp {
        return kotlinApp!!
    }

    fun getAppContext(): Context {
        return kotlinContext!!
    }


    fun getModelService(): ModelService {
        if (modelService == null) {
            modelService = ModelService(this)
        }
        return modelService!!
    }

    fun runOnUiThread(runnable: Runnable) {
        kotlinHandler!!.post(runnable)
    }


}
