package com.route.store.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.route.store.R
import com.route.store.ui.EcomAppState
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
        when (homeUiState) {
            HomeUiState.EmptyHome -> {
                item {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.img_no_connection_bro),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(8.dp)
                        )
                    }
                }
            }

            HomeUiState.Loading -> { /*TODO("make effect")*/
            }

            is HomeUiState.Success -> {

                item {
                    OfferCard(
                        onClick = { appState.navigateToProducts("0", "") },
                        imgId = images[currentIndex]
                    )
                }

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