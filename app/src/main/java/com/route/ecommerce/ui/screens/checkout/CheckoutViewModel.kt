package com.route.ecommerce.ui.screens.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.data.reposetory.CartRepository
import com.route.data.reposetory.OrderRepository
import com.route.datastore.UserPreferencesRepository
import com.route.network.model.OrderRequest
import com.route.network.model.ShippingAddress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
) : ViewModel() {

    fun createOrder(cartId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val token = userPreferencesRepository.getToken()
            val orderCreatedSuccessfully = orderRepository.createCashOrder(
                token,
                cartId,
                OrderRequest(ShippingAddress("details", "Phone", "city")),
            )
            cartRepository.clearCart()
        }
    }
}
