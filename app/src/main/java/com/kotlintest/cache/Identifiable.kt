package com.kotlintest.cache

/**
 * For collections. Some objects are the same(should be equal) even if their fields differs.
 */
interface Identifiable {

    /**
     * @return identifier, e.g. database id. Use as a key in maps
     */
    val identifier: Any
}
