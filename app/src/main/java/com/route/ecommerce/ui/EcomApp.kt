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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.route.ecommerce.navigation.EcomNavHost
import com.route.ecommerce.ui.utils.EcomNavRail
import com.route.ecommerce.ui.utils.EcomNavigationBar
import com.route.ecommerce.ui.utils.EcomTopBar

@Composable
fun EcomApp(
    appState: EcomAppState,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                EcomNavigationBar(
                    destinations = appState.topLevelDestinations,
                    onNavigateToDestination = appState::navigateToTopLevelDestinations,
                    currentDestination = appState.currentDestination,
                    modifier = Modifier.animateContentSize()
                )
                HorizontalDivider()
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
                EcomTopBar(
                    canGoToSearch = appState.canGoToSearch,
                    onNavigateToSearch = appState::navigateToSearch,
                    canNavigateUp = appState.canNavigateUp,
                    navigateUp = appState::navigateUp
                )
                HorizontalDivider()
                EcomNavHost(
                    appState = appState,
                    modifier = Modifier.consumeWindowInsets(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Top
                        )
                    )
                )
            }
        }
    }
}