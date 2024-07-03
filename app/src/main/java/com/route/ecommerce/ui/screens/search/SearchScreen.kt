package com.route.ecommerce.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.route.ecommerce.ui.EcomAppState
import com.route.ecommerce.ui.components.SearchItem
import com.route.ecommerce.ui.components.SearchToolBar

@Composable
fun SearchScreen(
    appState: EcomAppState,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchQuery by viewModel.query.collectAsState()
    val searchResultUiState by viewModel.searchResultUiState.collectAsState()
    val recentSearchUiState by viewModel.recentSearchQueryUiState.collectAsState()
    Column(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
        SearchToolBar(
            query = searchQuery,
            onQueryChange = viewModel::onQueryChange,
            onSearch = viewModel::onSearchTriggered,
            onBackClick = appState::navigateUp
        )
        when (searchResultUiState) {
            SearchResultUiState.LoadFailed,
            SearchResultUiState.Loading -> Unit

            SearchResultUiState.EmptyQuery -> {
                if (recentSearchUiState is RecentSearchUiState.Success) {
                    RecentSearchBody(
                        recentQueries = (recentSearchUiState as RecentSearchUiState.Success)
                            .recentSearchQueries.map { it.query },
                        onClearRecentSearch = viewModel::clearRecentSearch,
                        onRecentSearchClick = {
                            viewModel.onQueryChange(it)
                            viewModel.onSearchTriggered(it)
                        }
                    )
                }
            }

            is SearchResultUiState.Success -> {
                if ((searchResultUiState as SearchResultUiState.Success).isEmpty()) {
                    Column {
                        EmptySearchResult(query = searchQuery)
                        if (recentSearchUiState is RecentSearchUiState.Success) {
                            RecentSearchBody(
                                recentQueries = (recentSearchUiState as RecentSearchUiState.Success)
                                    .recentSearchQueries.map { it.query },
                                onClearRecentSearch = viewModel::clearRecentSearch,
                                onRecentSearchClick = {
                                    viewModel.onQueryChange(it)
                                    viewModel.onSearchTriggered(it)
                                }
                            )
                        }
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(330.dp),
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            (searchResultUiState as? SearchResultUiState.Success)?.products
                                ?: emptyList()
                        ) { product ->
                            SearchItem(
                                product = product,
                                onItemClick = {
                                    viewModel.onSearchTriggered(searchQuery)
                                    appState.navigateToProductDetails(id = product.id)
                                },
                                addToCart = viewModel::addCartProduct,
                                removeFromCart = viewModel::removeCartProduct,
                            )
                        }
                    }
                }
            }
        }
    }
}
