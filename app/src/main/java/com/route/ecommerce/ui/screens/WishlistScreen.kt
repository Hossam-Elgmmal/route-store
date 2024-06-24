package com.route.ecommerce.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.route.ecommerce.ui.EcomAppState

@Composable
fun WishlistScreen(
    appState: EcomAppState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = "Wishlist Screen")
    }
}