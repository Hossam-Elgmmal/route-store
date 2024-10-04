package com.route.ecommerce.ui.screens.products

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.route.data.model.Product
import com.route.ecommerce.R
import com.route.ecommerce.ui.EcomAppState
import com.route.ecommerce.ui.components.PriceText
import com.route.ecommerce.ui.components.RatingRow

@Composable
fun ProductsScreen(
    appState: EcomAppState,
    brandId: String,
    categoryId: String,
    modifier: Modifier = Modifier,
    viewModel: ProductsViewModel = hiltViewModel()
) {
    var shownBrand by rememberSaveable { mutableStateOf(brandId) }
    var shownCategory by rememberSaveable { mutableStateOf(categoryId) }
    val uiState by remember(shownBrand, shownCategory) {
        viewModel.getUiState(
            shownBrand,
            shownCategory
        )
    }.collectAsState()

    when (uiState) {
        ProductsUiState.Loading -> { /*TODO("loading effect")*/
        }

        is ProductsUiState.Success -> {
            val uiSuccessState = uiState as ProductsUiState.Success
            LazyVerticalGrid(
                columns = GridCells.Adaptive(180.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = modifier.padding(horizontal = 8.dp)
            ) {
                items(uiSuccessState.productsList) { product ->
                    ProductItem(
                        product = product,
                        onClick = appState::navigateToProductDetails
                    )
                }
                if (uiSuccessState.productsList.isEmpty()) {
                    item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Card(
                                modifier = Modifier.align(Alignment.Center),
                                shape = MaterialTheme.shapes.extraSmall
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.size(300.dp)
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.img_no_items),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .padding(8.dp)
                                    )
                                    Text("no items Found")
                                    ElevatedButton(
                                        onClick = {
                                            shownBrand = ""
                                            shownCategory = ""
                                        }
                                    ) {
                                        Text("show all products")
                                    }
                                }
                            }
                        }
                    }
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

@Composable
fun ProductItem(
    product: Product,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        onClick = { onClick(product.id) },
        modifier = modifier,
        shape = MaterialTheme.shapes.extraSmall
    ) {
        var isError by remember {
            mutableStateOf(false)
        }
        val imageLoader = rememberAsyncImagePainter(
            model = product.imageCoverUrl,
            onState = { state ->
                isError = state is AsyncImagePainter.State.Error
            }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = imageLoader,
                contentDescription = null,
                modifier = Modifier
                    .width(174.dp)
                    .height(240.dp)
                    .clip(MaterialTheme.shapes.extraSmall)
            )
            Text(
                text = product.title,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            RatingRow(
                rating = product.ratingsAverage,
                ratingQuantity = product.ratingsQuantity,
                modifier = Modifier.graphicsLayer {
                    scaleX = 0.9f
                    scaleY = 0.9f
                }
            )
            PriceText(
                product.price,
                modifier = Modifier.graphicsLayer {
                    scaleX = 0.8f
                    scaleY = 0.8f
                }
            )
        }
    }
}