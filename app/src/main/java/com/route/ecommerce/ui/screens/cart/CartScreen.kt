package com.route.ecommerce.ui.screens.cart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CartScreen(
    navigateToLogin: () -> Unit,
    navigateToProductDetails: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = "Cart Screen")
        Button(onClick = navigateToLogin) {
            Text(text = "login")
        }
        Button(onClick = navigateToProductDetails) {
            Text(text = "product details")
        }
    }
}