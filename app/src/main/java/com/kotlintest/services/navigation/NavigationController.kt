package com.kotlintest.services.navigation

import android.os.Bundle

interface NavigationController {
    fun navigateTo(screen: Screen, type: ScreenType)
    fun navigateTo(screen: Screen, type: ScreenType, bundle: Bundle?)
}
