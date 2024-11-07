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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.route.ecommerce.R
import com.route.ecommerce.ui.EcomAppState
import com.route.ecommerce.ui.components.CartItem
import com.route.ecommerce.ui.components.EcomErrorDialog
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
    val token by viewModel.token.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isOffline by appState.isOffline.collectAsState()
    val onlineCart by viewModel.onlineCart.collectAsState()

    var showErrorDialog by remember { mutableStateOf(false) }

    BackHandler {
        onBackPressed()
    }
    when (cartUiState) {
        CartUiState.EmptyCart -> {
            EmptyCartBody()
        }

        CartUiState.Loading -> {}
        is CartUiState.Success -> {
            val successUiState = (cartUiState as CartUiState.Success)

            val subtotal =
                calculateSubTotal(successUiState.cartProductsMap, successUiState.products)
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                SubtotalBody(
                    subtotal = subtotal,
                    onCheckout = {
                        if (token.isEmpty()) {
                            appState.navigateToLogin()
                        } else if (isOffline) {
                            showErrorDialog = true
                        } else {
                            viewModel.uploadCart(token, successUiState.cartProductsList)
                        }
                    }
                )
                LaunchedEffect(onlineCart) {
                    onlineCart?.let {
                        if (it.cartId.isNotEmpty() && it.ownerId.isNotEmpty()) {
                            viewModel.setUserId(it.ownerId)
                            appState.navigateToCheckout(it.cartId)
                            viewModel.resetCart()
                        } else {
                            showErrorDialog = true
                        }
                    }
                }
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = modifier.fillMaxWidth()
                ) {
                    items(
                        items = successUiState.products,
                        key = { it.id },
                    ) { product ->
                        val productCount = successUiState.cartProductsMap[product.id] ?: 0
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
                if (isLoading) {
                    AlertDialog(
                        onDismissRequest = {},
                        confirmButton = {},
                        icon = {
                            CircularProgressIndicator()
                        },
                        title = {
                            Text(stringResource(R.string.almost_done))
                        },
                        text = {
                            Text(stringResource(R.string.this_may_take_up_to_1_minute))
                        }
                    )
                }
                if (showErrorDialog) {
                    EcomErrorDialog(
                        iconId = R.drawable.ic_error,
                        textId = R.string.unexpected_error,
                        onDismissRequest = {
                            showErrorDialog = false
                            viewModel.resetCart()
                        },
                    )
                }
            }
        }
    }
}