package com.route.ecommerce.ui.screens.wishlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun WishlistScreen(
    navigateToProductDetails: () -> Unit,
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = "Wishlist Screen")
        Button(onClick = navigateToProductDetails) {
            Text(text = "product details")
        }
        Button(onClick = navigateToLogin) {
            Text(text = "login")
        }
    }
}