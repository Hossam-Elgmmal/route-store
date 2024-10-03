package com.route.ecommerce.ui.screens.productDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.data.model.Product
import com.route.data.reposetory.CartRepository
import com.route.data.reposetory.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : ViewModel() {


    @OptIn(ExperimentalCoroutinesApi::class)
    fun getUiState(id: String): StateFlow<ProductDetailsUiState> {
        return productRepository.getProductById(id)
            .flatMapLatest { product ->
                cartRepository.getCartProducts().map { cartProductsList ->
                    val cartProduct = cartProductsList.find { it.id == id }
                    ProductDetailsUiState.Success(product, cartProduct?.count ?: 0)
                }
            }.stateIn(
                scope = viewModelScope,
                initialValue = ProductDetailsUiState.Loading,
                started = SharingStarted.WhileSubscribed(5_000)
            )
    }

    fun addCartProduct(productId: String, itemCount: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (itemCount == 0) {
                cartRepository.addCartProduct(productId)
            } else {
                cartRepository.plusOneCartProduct(productId)
            }
        }
    }

    fun plusOneCartProduct(productId: String) {
        viewModelScope.launch {
            cartRepository.plusOneCartProduct(productId)
        }
    }

    fun minusOneCartProduct(productId: String) {
        viewModelScope.launch {
            cartRepository.minusOneCartProduct(productId)
        }
    }

    fun updateCartItemCount(productId: String, count: Int) {
        viewModelScope.launch {
            cartRepository.updateCartProduct(productId, count)
        }
    }

    fun removeCartItem(productId: String) {
        viewModelScope.launch {
            cartRepository.removeCartProduct(productId)
        }
    }

}

sealed interface ProductDetailsUiState {
    data object Loading : ProductDetailsUiState
    data class Success(
        val product: Product,
        val countInCart: Int,
    ) : ProductDetailsUiState
}