package com.kotlintest.cache

import java.lang.ref.WeakReference

abstract class WeakHolder<T> : Holder<T> {
    private var payloadWeak: WeakReference<T>? = null

    override fun get(): T {
        var payload: T? = if (payloadWeak == null) null else payloadWeak!!.get()

        if (payload == null) {
            payload = create()
            payloadWeak = WeakReference<T>(payload)
        }

        return payload!!
    }

    override val dontCreate: T?
        get() = if (payloadWeak == null) null else payloadWeak!!.get()

    protected abstract fun create(): T
}
