package com.route.ecommerce.ui.screens.products

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProductsScreen(
    navigateToProductDetails: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = "Products Screen")
        Button(
            onClick = navigateToProductDetails
        ) {
            Text(text = "Product Details")
        }
    }
}