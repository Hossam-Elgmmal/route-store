package com.route.ecommerce.ui.screens.cart

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.route.ecommerce.ui.EcomAppState
import com.route.ecommerce.ui.components.CartItem
import kotlinx.coroutines.CoroutineScope

@Composable
fun CartScreen(
    appState: EcomAppState,
    onBackPressed: () -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = hiltViewModel(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
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
            val products = (cartUiState as CartUiState.Success).products
            val cartProductsMap =
                (cartUiState as CartUiState.Success).cartProductsMap

            val subtotal = calculateSubTotal(cartProductsMap, products)
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
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
                        val productCount = cartProductsMap[product.id] ?: 0
                        CartItem(
                            product = product,
                            countInCart = productCount,
                            onCountClick = { /*TODO()*/ },
                            onItemClick = { appState.navigateToProductDetails(product.id) },
                            upsertCartProduct = viewModel::upsertCartProduct,
                            snackbarHostState = snackbarHostState,
                            coroutineScope = coroutineScope
                        )
                    }
                    item { Spacer(Modifier.height(8.dp)) }
                }
            }
        }
    }
}