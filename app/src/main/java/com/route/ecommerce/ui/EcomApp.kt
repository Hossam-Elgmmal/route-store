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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.route.ecommerce.R
import com.route.ecommerce.navigation.EcomNavHost
import com.route.ecommerce.ui.utils.EcomNavRail
import com.route.ecommerce.ui.utils.EcomNavigationBar
import com.route.ecommerce.ui.utils.EcomTopBar

@Composable
fun EcomApp(
    appState: EcomAppState,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val isOffline by appState.isOffline.collectAsState()
    val notConnectedMessage = stringResource(id = R.string.not_connected)

    LaunchedEffect(key1 = isOffline) {
        if (isOffline) {
            snackbarHostState.showSnackbar(
                message = notConnectedMessage,
                duration = SnackbarDuration.Indefinite
            )
        }
    }

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            if (appState.shouldShowTopBar) {
                EcomTopBar(
                    canGoToSearch = appState.canGoToSearch,
                    onNavigateToSearch = appState::navigateToSearch,
                    canNavigateUp = appState.canNavigateUp,
                    navigateUp = appState::navigateUp,
                    modifier = Modifier.animateContentSize()
                )
            }
        },
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
                EcomNavHost(
                    appState = appState,
                    modifier = if (appState.shouldShowTopBar) {
                        Modifier.consumeWindowInsets(
                            WindowInsets.safeDrawing.only(
                                WindowInsetsSides.Top
                            )
                        )
                    } else {
                        Modifier
                    },
                )
            }
        }
    }
}