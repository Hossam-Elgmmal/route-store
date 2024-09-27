package com.route.ecommerce.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.data.model.Brand
import com.route.data.model.Product
import com.route.data.reposetory.BrandRepository
import com.route.data.reposetory.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    productRepository: ProductRepository,
    brandRepository: BrandRepository,
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val homeUiState: StateFlow<HomeUiState> =
        productRepository.getProducts().flatMapLatest { productList ->
            brandRepository.getBrands().map { brandList ->
                if (productList.isEmpty() || brandList.isEmpty()) {
                    HomeUiState.EmptyHome
                } else {
                    HomeUiState.Success(
                        products = productList,
                        brands = brandList
                    )
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState.Loading
        )
}

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data object EmptyHome : HomeUiState
    data class Success(
        val products: List<Product> = emptyList(),
        val brands: List<Brand> = emptyList(),
    ) : HomeUiState
}