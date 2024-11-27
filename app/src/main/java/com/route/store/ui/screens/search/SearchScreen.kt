package com.route.store.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.route.store.ui.EcomAppState
import com.route.store.ui.components.SearchItem
import com.route.store.ui.components.SearchTextField
import kotlinx.coroutines.CoroutineScope

@Composable
fun SearchScreen(
    appState: EcomAppState,
    cartItems: Map<String, Int>,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val searchQuery by viewModel.query.collectAsState()
    val searchResultUiState by viewModel.searchResultUiState.collectAsState()
    val recentSearchUiState by viewModel.recentSearchQueryUiState.collectAsState()
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SearchTextField(
            query = searchQuery,
            onQueryChange = viewModel::onQueryChange,
            onSearch = viewModel::onSearchTriggered,
            modifier = Modifier.padding(horizontal = 16.dp)
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
                                ?: emptyList(),
                            key = { it.id }
                        ) { product ->
                            val itemCountInCart = cartItems[product.id] ?: 0
                            SearchItem(
                                product = product,
                                countInCart = itemCountInCart,
                                onItemClick = {
                                    viewModel.onSearchTriggered(searchQuery)
                                    appState.navigateToProductDetails(id = product.id)
                                },
                                upsertCartProduct = viewModel::upsertCartProduct,
                                coroutineScope = coroutineScope,
                                snackbarHostState = snackbarHostState,
                            )
                        }
                    }
                }
            }
        }
    }
}
