package com.route.ecommerce.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.route.ecommerce.navigation.EcomNavHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcomApp(
    appState: EcomAppState,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                BottomAppBar {
                    Text(text = "Bottom Bar")
                }
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
                NavigationRail {
                    Text(text = "Nav Rail")
                }
            }
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                if (appState.shouldShowAppBar) {
                    TopAppBar(
                        title = { Text(text = "top app bar") }
                    )
                }
                EcomNavHost(appState)
            }
        }
    }
}