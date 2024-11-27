package com.route.store.ui.screens.checkout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    var uiState: CheckoutUiState by mutableStateOf(CheckoutUiState.Idle)

    var address by mutableStateOf("")
    var isAddressError by mutableStateOf(false)

    var city by mutableStateOf("")
    var isCityError by mutableStateOf(false)

    var phone by mutableStateOf("")
    var isPhoneError by mutableStateOf(false)

    fun resetUiState() {
        uiState = CheckoutUiState.Idle
        clearAddress()
        clearAddress()
        clearPhone()
        isCityError = false
        isAddressError = false
        isPhoneError = false
    }

    fun updateAddress(newValue: String) {
        address = newValue
    }

    fun updateCity(newValue: String) {
        city = newValue
    }

    fun clearCity() {
        city = ""
    }

    fun updatePhone(newValue: String) {
        if (newValue.all { it.isDigit() }) {
            phone = newValue
        }
    }

    fun clearAddress() {
        address = ""
    }

    fun clearPhone() {
        phone = ""
    }
    fun createOrder(cartId: String) {

        isAddressError = address.trim().isEmpty()
        isCityError = city.trim().isEmpty()
        isPhoneError = phone.length != 11

        if (isAddressError || isCityError || isPhoneError)
            return

        viewModelScope.launch(Dispatchers.IO) {
            uiState = CheckoutUiState.Loading
            val token = userPreferencesRepository.getToken()
            val orderCreatedSuccessfully = orderRepository.createCashOrder(
                token,
                cartId,
                OrderRequest(ShippingAddress(address, phone, city)),
            )
            if (orderCreatedSuccessfully) {
                cartRepository.clearCart()
                uiState = CheckoutUiState.Success
                refreshOrders()
            } else {
                uiState = CheckoutUiState.Error
            }
        }
    }

    private fun refreshOrders() {
        viewModelScope.launch(Dispatchers.IO) {
            orderRepository.refreshOrders()
            uiState = CheckoutUiState.Navigate
        }
    }
}

sealed interface CheckoutUiState {
    data object Idle : CheckoutUiState
    data object Loading : CheckoutUiState
    data object Success : CheckoutUiState
    data object Navigate : CheckoutUiState
    data object Error : CheckoutUiState
}