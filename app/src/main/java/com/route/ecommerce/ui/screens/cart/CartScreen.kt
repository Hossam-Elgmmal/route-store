package com.route.ecommerce.ui.screens.cart

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.route.ecommerce.ui.EcomAppState

@Composable
fun CartScreen(
    appState: EcomAppState,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = hiltViewModel()
) {
    BackHandler {
        onBackPressed()
    }
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = "Cart Screen")
        Button(onClick = appState::navigateToProductDetails) {
            Text(text = "product details")
        }
        Button(onClick = appState::navigateToWishlist) {
            Text(text = "Wishlist screen")
        }
        Button(onClick = appState::navigateToCheckout) {
            Text(text = "Checkout screen")
        }
    }
}