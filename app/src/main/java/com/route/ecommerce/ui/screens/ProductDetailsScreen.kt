package com.route.ecommerce.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.route.ecommerce.ui.EcomAppState

@Composable
fun ProductDetailsScreen(
    appState: EcomAppState,
    productId: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = "Product Details Screen")
        Text(text = productId)
    }
}