package com.route.ecommerce.ui.screens.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.data.model.Product
import com.route.data.model.SearchQuery
import com.route.data.reposetory.ProductRepository
import com.route.data.reposetory.RecentSearchQueryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val recentSearchQueryRepository: RecentSearchQueryRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val query = savedStateHandle.getStateFlow(key = SEARCH_QUERY, initialValue = "")

    val searchResultUiState: StateFlow<SearchResultUiState> =
        query.map { searchText ->
            if (searchText.trim().isEmpty()) {
                SearchResultUiState.EmptyQuery
            } else {
                val list = productRepository.searchProducts(searchText)
                SearchResultUiState.Success(list)
            }
        }.catch { SearchResultUiState.LoadFailed }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SearchResultUiState.Loading
            )

    val recentSearchQueryUiState: StateFlow<RecentSearchUiState> =
        recentSearchQueryRepository.getSearchQuery(5)
            .map(RecentSearchUiState::Success)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = RecentSearchUiState.Loading
            )

    fun onQueryChange(newQuery: String) {
        savedStateHandle[SEARCH_QUERY] = newQuery
    }

    fun onSearchTriggered(query: String) {
        if (query.trim().isNotEmpty()) {
            viewModelScope.launch {
                recentSearchQueryRepository.insertSearchQuery(query.trim())
            }
        }
    }

    fun clearRecentSearch() {
        viewModelScope.launch {
            recentSearchQueryRepository.clearSearchQuery()
        }
    }
}

private const val SEARCH_QUERY = "search_query"

sealed interface SearchResultUiState {
    data object Loading : SearchResultUiState
    data object LoadFailed : SearchResultUiState
    data object EmptyQuery : SearchResultUiState
    data class Success(
        val products: List<Product> = emptyList()
    ) : SearchResultUiState {
        fun isEmpty() = products.isEmpty()
    }
}

sealed interface RecentSearchUiState {
    data object Loading : RecentSearchUiState
    data class Success(
        val recentSearchQueries: List<SearchQuery> = emptyList()
    ) : RecentSearchUiState
}