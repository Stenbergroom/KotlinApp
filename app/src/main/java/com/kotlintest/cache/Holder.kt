package com.kotlintest.cache

interface Holder<T> {
    fun get(): T
    val dontCreate: T?
}
