package com.route.ecommerce.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.route.ecommerce.ui.EcomAppState
import com.route.ecommerce.ui.auth.forgotPasswordScreen
import com.route.ecommerce.ui.auth.loginScreen
import com.route.ecommerce.ui.auth.signupScreen

@Composable
fun EcomNavHost(
    appState: EcomAppState,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = appState.navController,
        startDestination = TopLevelDestination.HOME.name,
        modifier = modifier,
        enterTransition = {
            slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(300))
        },
        exitTransition = {
            slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(300))
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(300))
        },
        popExitTransition = {
            slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(300))
        }
    ) {
        homeScreen(
            appState = appState
        )
        menuScreen(
            appState = appState,
            onBackPressed = onBackPressed
        )
        cartScreen(
            appState = appState,
            onBackPressed = onBackPressed
        )
        accountScreen(
            appState = appState,
            onBackPressed = onBackPressed
        )
        loginScreen(appState)
        signupScreen(appState)
        forgotPasswordScreen(appState)
        productsScreen(appState)
        productDetailsScreen(appState)
        searchScreen(appState)
        wishlistScreen(appState)
        checkoutScreen(appState)
    }
}