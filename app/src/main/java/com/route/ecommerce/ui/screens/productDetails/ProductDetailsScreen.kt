package com.route.ecommerce.ui.screens.productDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.route.ecommerce.ui.EcomAppState
import com.route.ecommerce.ui.components.AddRemoveCard
import com.route.ecommerce.ui.components.ProductData
import com.route.ecommerce.ui.components.ProductImages
import kotlinx.coroutines.CoroutineScope

@Composable
fun ProductDetailsScreen(
    appState: EcomAppState,
    productId: String,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    viewModel: ProductDetailsViewModel = hiltViewModel(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) {
    val uiState by remember(productId) { viewModel.getUiState(productId) }.collectAsState()
    when (uiState) {
        ProductDetailsUiState.Loading -> { /*TODO("loading effect")*/
        }

        is ProductDetailsUiState.Success -> {
            val uiSuccessState = uiState as ProductDetailsUiState.Success
            LazyVerticalGrid(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                columns = GridCells.Adaptive(310.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    ProductImages(
                        product = uiSuccessState.product,
                        modifier = Modifier.wrapContentWidth()
                    )
                }
                item {
                    ProductData(
                        product = uiSuccessState.product,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                item(
                    span = { GridItemSpan(maxLineSpan) },
                ) {
                    AddRemoveCard(
                        product = uiSuccessState.product,
                        upsertCartProduct = viewModel::upsertCartProduct,
                        countInCart = uiSuccessState.countInCart,
                        onCountClick = {},
                        snackbarHostState = snackbarHostState,
                        coroutineScope = coroutineScope,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
                item(
                    span = { GridItemSpan(maxLineSpan) }
                ) {
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}