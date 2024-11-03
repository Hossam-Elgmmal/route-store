package com.route.ecommerce.ui.screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.data.model.CartProduct
import com.route.data.model.Product
import com.route.data.reposetory.CartRepository
import com.route.data.reposetory.ProductRepository
import com.route.datastore.UserPreferencesRepository
import com.route.network.model.Cart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
    userPreferencesRepository: UserPreferencesRepository,
) : ViewModel() {

    val token: StateFlow<String> = userPreferencesRepository.userData
        .map {
            it.userToken
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ""
        )

    private val _onlineCart = MutableStateFlow<Cart?>(null)
    val onlineCart: StateFlow<Cart?>
        get() = _onlineCart.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
        get() = _isLoading.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val cartUiState: StateFlow<CartUiState> = cartRepository.getCartProducts()
        .flatMapLatest { cartProductsList ->
            val cartProductsMap = cartProductsList.associate { it.id to it.count }
            productRepository.getProductsInCart(cartProductsMap.keys.toList()).map { products ->
                if (cartProductsList.isEmpty()) {
                    CartUiState.EmptyCart
                } else {
                    CartUiState.Success(cartProductsMap, products, cartProductsList)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = CartUiState.Loading
        )

    fun upsertCartProduct(productId: String, count: Int) {
        viewModelScope.launch {
            cartRepository.upsertCartProduct(productId, count)
        }
    }

    fun resetCart() {
        viewModelScope.launch {
            _onlineCart.update { null }
        }
    }

    fun uploadCart(token: String, cartProductsList: List<CartProduct>) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.update { true }
            _onlineCart.update { cartRepository.uploadCart(cartProductsList.toSet(), token) }
            _isLoading.update { false }
        }
    }
}

sealed interface CartUiState {
    data object Loading : CartUiState
    data object EmptyCart : CartUiState
    data class Success(
        val cartProductsMap: Map<String, Int>,
        val products: List<Product>,
        val cartProductsList: List<CartProduct>,
    ) : CartUiState
}
