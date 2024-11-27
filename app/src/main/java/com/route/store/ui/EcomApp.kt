package com.route.store.ui

import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.route.store.R
import com.route.store.navigation.EcomNavHost
import com.route.store.navigation.TopLevelDestination
import com.route.store.ui.components.EcomBackground
import com.route.store.ui.components.EcomNavRail
import com.route.store.ui.components.EcomNavigationBar
import com.route.store.ui.components.EcomTopBar
import com.route.store.ui.screens.settings.SettingsDialog

@Composable
fun EcomApp(
    appState: EcomAppState,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val isOffline by appState.isOffline.collectAsState()
    val notConnectedMessage = stringResource(id = R.string.not_connected)
    val cartItems by appState.cartMap.collectAsState()
    val cartCount = cartItems.values.sum()

    LaunchedEffect(key1 = isOffline) {
        if (isOffline) {
            snackbarHostState.showSnackbar(
                message = notConnectedMessage,
                duration = SnackbarDuration.Short
            )
        }
    }

    var latestTopLevelDestination by
    rememberSaveable { mutableStateOf(TopLevelDestination.HOME) }

    var showSettingsDialog by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        appState.navController.addOnDestinationChangedListener { _, navDestination, _ ->
            when (navDestination.route) {
                TopLevelDestination.HOME.name ->
                    latestTopLevelDestination = TopLevelDestination.HOME

                TopLevelDestination.MENU.name ->
                    latestTopLevelDestination = TopLevelDestination.MENU

                TopLevelDestination.CART.name ->
                    latestTopLevelDestination = TopLevelDestination.CART

                TopLevelDestination.ACCOUNT.name ->
                    latestTopLevelDestination = TopLevelDestination.ACCOUNT

                else -> {}
            }
        }
    }
    EcomBackground {
        Scaffold(
            modifier = modifier,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            containerColor = Color.Transparent,
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            bottomBar = {
                if (appState.shouldShowBottomBar) {
                    EcomNavigationBar(
                        destinations = appState.topLevelDestinations,
                        onNavigateToDestination = { destination, selected ->
                            appState.navigateToTopLevelDestinations(destination, selected)
                            latestTopLevelDestination = destination
                        },
                        latestTopLevelDestination = latestTopLevelDestination,
                        cartCount = cartCount,
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
                        onNavigateToDestination = { destination, selected ->
                            appState.navigateToTopLevelDestinations(destination, selected)
                            latestTopLevelDestination = destination
                        },
                        latestTopLevelDestination = latestTopLevelDestination,
                        modifier = Modifier
                            .safeDrawingPadding(),
                        cartCount = cartCount,
                    )
                }
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    EcomTopBar(
                        canGoToSearch = appState.canGoToSearch,
                        canShowSettings = appState.canShowSettings,
                        onNavigateToSearch = appState::navigateToSearch,
                        onSettingsClick = { showSettingsDialog = true },
                        canNavigateUp = appState.canNavigateUp,
                        navigateUp = appState::navigateUp,
                    )
                    Box(
                        modifier = Modifier.consumeWindowInsets(
                            WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
                        )
                    ) {
                        EcomNavHost(
                            appState = appState,
                            onBackPressed = {
                                appState.navigateToTopLevelDestinations(
                                    TopLevelDestination.HOME,
                                    false
                                )
                                latestTopLevelDestination = TopLevelDestination.HOME
                            },
                            cartItems = cartItems,
                            snackbarHostState = snackbarHostState,
                        )
                    }
                }
            }
        }
        if (showSettingsDialog) {
            SettingsDialog(
                onDismiss = { showSettingsDialog = false },
            )
        }
    }
}