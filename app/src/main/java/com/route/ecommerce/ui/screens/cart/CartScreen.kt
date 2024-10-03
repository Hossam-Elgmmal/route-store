package com.route.ecommerce.ui.screens.cart

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.route.ecommerce.ui.EcomAppState
import com.route.ecommerce.ui.components.CartItem

@Composable
fun CartScreen(
    appState: EcomAppState,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = hiltViewModel()
) {
    val cartUiState by viewModel.cartUiState.collectAsState()

    BackHandler {
        onBackPressed()
    }
    when (cartUiState) {
        CartUiState.EmptyCart -> {
            EmptyCartBody()
        }

        CartUiState.Loading -> {}
        is CartUiState.Success -> {
            val products = (cartUiState as? CartUiState.Success)?.products ?: emptyList()
            val cartProductsMap =
                (cartUiState as? CartUiState.Success)?.cartProductsMap ?: emptyMap()

            val subtotal = calculateSubTotal(cartProductsMap, products)
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                SubtotalBody(
                    subtotal = subtotal,
                    onCheckout = appState::navigateToCheckout
                )
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = modifier.fillMaxWidth()
                ) {
                    items(
                        items = products,
                        key = { it.id },
                    ) { product ->
                        CartItem(
                            product = product,
                            countInCart = cartProductsMap[product.id] ?: 0,
                            onCountClick = { /*TODO()*/ },
                            onItemClick = { appState.navigateToProductDetails(product.id) },
                            onPlusClick = viewModel::plusOneCartProduct,
                            onMinusClick = viewModel::minusOneCartProduct,
                            onDeleteClick = viewModel::removeCartItem,
                        )
                    }
                }
            }
        }
    }
}