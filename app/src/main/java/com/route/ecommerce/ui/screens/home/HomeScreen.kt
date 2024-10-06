package com.route.ecommerce.ui.screens.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.route.ecommerce.R
import com.route.ecommerce.ui.EcomAppState
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    appState: EcomAppState,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    var currentIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    val images = listOf(
        R.drawable.img_bag,
        R.drawable.img_headphones,
        R.drawable.img_perfume,
    )
    val homeUiState by viewModel.homeUiState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        while (true) {
            delay(3_500)
            currentIndex = (currentIndex + 1) % images.size
        }
    }
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
    ) {
        item {
            OfferCard(
                onClick = {
                    /*TODO()*/
                },
                imgId = images[currentIndex]
            )
        }
        when (homeUiState) {
            HomeUiState.EmptyHome -> {}

            HomeUiState.Loading -> { /*TODO("make effect")*/
            }

            is HomeUiState.Success -> {
                item {
                    HomeItemsRow(
                        onItemClick = appState::navigateToProductDetails,
                        titleId = R.string.best_sellers,
                        productList =
                        (homeUiState as HomeUiState.Success).products
                            .sortedBy { it.quantity }.reversed().subList(0, 12)
                    )
                }
                item {
                    HomeItemsRow(
                        onItemClick = appState::navigateToProductDetails,
                        titleId = R.string.most_popular,
                        productList =
                        (homeUiState as HomeUiState.Success).products
                            .filter { it.ratingsAverage >= 4.5 }.reversed()
                    )
                }
                item {
                    HomeItemsRow(
                        onItemClick = appState::navigateToProductDetails,
                        titleId = R.string.new_arrivals,
                        productList =
                        (homeUiState as HomeUiState.Success).products
                            .sortedBy { it.sold }.reversed().subList(0, 12)
                    )
                }
                item {
                    HomeBrandsRow(
                        onItemClick = appState::navigateToProducts,
                        brandsList =
                        (homeUiState as HomeUiState.Success).brands
                    )
                }
            }
        }
    }
}