package com.route.ecommerce.ui.screens.checkout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.route.ecommerce.ui.EcomAppState

@Composable
fun CheckoutScreen(
    appState: EcomAppState,
    cartId: String,
    modifier: Modifier = Modifier,
    viewModel: CheckoutViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = "Checkout Screen cartId = $cartId")
    }
}