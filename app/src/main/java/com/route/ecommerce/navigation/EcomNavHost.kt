package com.route.ecommerce.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.route.ecommerce.ui.EcomAppState

@Composable
fun EcomNavHost(
    appState: EcomAppState,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = appState.navController,
        startDestination = TopLevelDestination.HOME.name,
        modifier = modifier,
        enterTransition = {
            fadeIn(animationSpec = tween(300))
        },
        exitTransition = {
            fadeOut(tween(300))
        }
    ) {
        homeScreen(
            appState = appState
        )
        accountScreen(
            appState = appState
        )
        menuScreen()
        cartScreen()
        productsScreen()
        productDetailsScreen()
        wishlistScreen()
        searchScreen()
    }
}