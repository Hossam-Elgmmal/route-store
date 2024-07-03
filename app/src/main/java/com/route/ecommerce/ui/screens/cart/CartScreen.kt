package com.route.ecommerce.ui.screens.cart

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    val cartProducts by viewModel.cartProducts.collectAsState()

    BackHandler {
        onBackPressed()
    }
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = "Cart Screen")
        Button(onClick = { appState.navigateToProductDetails("123") }) {
            Text(text = "product details")
        }
        Button(onClick = appState::navigateToWishlist) {
            Text(text = "Wishlist screen")
        }
        Button(onClick = appState::navigateToCheckout) {
            Text(text = "Checkout screen")
        }
        LazyColumn {
            items(cartProducts) {
                Text(text = it.id)
                Text(text = it.count.toString())
            }
        }
    }
}