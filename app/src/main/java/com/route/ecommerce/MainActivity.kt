package com.route.ecommerce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.route.ecommerce.ui.EcomApp
import com.route.ecommerce.ui.rememberEcomAppState
import com.route.ecommerce.ui.theme.EcomTheme

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val appState = rememberEcomAppState(
                windowSizeClass = calculateWindowSizeClass(activity = this)
            )
            EcomTheme {
                EcomApp(appState)
            }
        }
    }
}