package com.route.ecommerce.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.route.ecommerce.navigation.EcomNavHost
import com.route.ecommerce.ui.utils.EcomBottomBar
import com.route.ecommerce.ui.utils.EcomNavRail
import com.route.ecommerce.ui.utils.EcomTopBar

@Composable
fun EcomApp(
    appState: EcomAppState,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                EcomBottomBar(
                    destinations = appState.topLevelDestinations,
                    onNavigateToDestination = appState::navigateToTopLevelDestinations,
                    currentDestination = appState.currentDestination,
                    modifier = Modifier.animateContentSize()
                )
            }
        }
    ) { paddingValues ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal
                    )
                )
        ) {
            if (appState.shouldShowNavRail) {
                EcomNavRail(
                    destinations = appState.topLevelDestinations,
                    onNavigateToDestination = appState::navigateToTopLevelDestinations,
                    currentDestination = appState.currentDestination,
                    modifier = Modifier
                        .safeDrawingPadding()
                        .animateContentSize()
                )
            }
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                if (appState.shouldShowTopBar) {
                    EcomTopBar(
                        onNavigateToSearch = appState::navigateToSearch,
                        modifier = Modifier.animateContentSize()
                    )
                }
                EcomNavHost(
                    appState = appState,
                    modifier = if (appState.shouldShowTopBar)
                        Modifier.consumeWindowInsets(
                            WindowInsets.safeDrawing.only(
                                WindowInsetsSides.Top
                            )
                        ) else Modifier
                )
            }
        }
    }
}