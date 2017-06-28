package com.kotlintest.cache

interface MapHolder<K, V> {
    operator fun get(key: K): V

    fun getDontCreate(key: K): V?

    val allDontCreate: Collection<V>
}
