package com.kotlintest.cache

import java.lang.ref.WeakReference
import java.util.HashMap
import java.util.LinkedList

abstract class WeakMapHolder<K, V> : MapHolder<K, V> {
    private var map: MutableMap<Any, WeakReference<V>>? = null

    override fun get(key: K): V {
        val mapKey = getMapKey(key)

        if (map == null)
            map = HashMap<Any, WeakReference<V>>()

        val valueWeak = map!![mapKey]
        var value: V? = valueWeak?.get()

        if (value == null) {
            value = create(key)
            map!!.put(mapKey, WeakReference<V>(value))
        }

        return value!!
    }

    private fun getMapKey(key: K): Any {
        if (key is Identifiable) {
            return key.identifier
        } else {
            return key!!
        }
    }

    override fun getDontCreate(key: K): V? {
        if (map != null) {
            val valueWeak = map!![getMapKey(key)]
            return valueWeak?.get()
        }

        return null
    }

    override val allDontCreate: Collection<V>
        get() {
            val aliveValues = LinkedList<V>()
            if (map != null) {
                for (valueWeak in map!!.values) {
                    val value = valueWeak.get()
                    if (value != null)
                        aliveValues.add(value)
                }
            }
            return aliveValues
        }

    protected abstract fun create(key: K): V
}
