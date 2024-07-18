package com.route.ecommerce.ui.screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.data.model.Product
import com.route.data.reposetory.CartRepository
import com.route.data.reposetory.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val cartUiState: StateFlow<CartUiState> = cartRepository.getCartProducts()
        .flatMapLatest { cartProductsList ->
            val cartProductsMap = cartProductsList.associate { it.id to it.count }
            productRepository.getProductsInCart(cartProductsMap.keys.toList()).map { products ->
                if (cartProductsList.isEmpty()) {
                    CartUiState.EmptyCart
                } else {
                    CartUiState.Success(cartProductsMap, products)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = CartUiState.Loading
        )

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

sealed interface CartUiState {
    data object Loading : CartUiState
    data object EmptyCart : CartUiState
    data class Success(
        val cartProductsMap: Map<String, Int> = emptyMap(),
        val products: List<Product> = emptyList()
    ) : CartUiState
}
