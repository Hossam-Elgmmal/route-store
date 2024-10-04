package com.route.ecommerce.ui.screens.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.data.model.Product
import com.route.data.reposetory.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productRepository: ProductRepository,
) : ViewModel() {

    fun getUiState(
        brandId: String,
        categoryId: String,
    ): StateFlow<ProductsUiState> = productRepository.getProducts().map { allProducts ->
        if (brandId.isNotEmpty()) {
            val list = allProducts.filter { it.brandId == brandId }
            ProductsUiState.Success(list)
        } else if (categoryId.isNotEmpty()) {
            val list = allProducts.filter { it.categoryId == categoryId }
            ProductsUiState.Success(list)
        } else {
            ProductsUiState.Success(allProducts)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ProductsUiState.Loading
    )

}

sealed interface ProductsUiState {
    data object Loading : ProductsUiState
    data class Success(
        val productsList: List<Product>,
    ) : ProductsUiState
}