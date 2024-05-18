package com.route.ecommerce.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.route.ecommerce.ui.EcomAppState

@Composable
fun HomeScreen(
    appState: EcomAppState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        Text(text = "welcome home")
        Button(onClick = appState::navigateToLogin) {
            Text(text = "login")
        }
        Button(onClick = appState::navigateToSignup) {
            Text(text = "sign up")
        }
        Button(onClick = appState::navigateToProducts) {
            Text(text = "products")
        }
        Button(onClick = appState::navigateToProductDetails) {
            Text(text = "product details")
        }
        Button(onClick = appState::navigateToWishlist) {
            Text(text = "wishlist")
        }
    }
}