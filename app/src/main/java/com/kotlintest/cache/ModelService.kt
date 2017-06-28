package com.kotlintest

import com.kotlintest.cache.WeakHolder
import com.kotlintest.network.DefaultModel

/**
 * Created by alex on 6/26/17.
 */

class ModelService(var parent: Any) {

    private val defaultModel = object : WeakHolder<DefaultModel>() {
        override fun create(): DefaultModel {
            return DefaultModel()
        }
    }

    fun getDefaultModel(): DefaultModel {
        val defaultModel = this.defaultModel.get()
        defaultModel.reloadModel()
        return defaultModel
    }

}
