package com.route.ecommerce.ui.screens.orders

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.data.model.Order
import com.route.data.model.Product
import com.route.data.reposetory.OrderRepository
import com.route.data.reposetory.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository,
) : ViewModel() {
    val userOrders: StateFlow<List<Order>> =
        orderRepository.getOrders().map {
            it.reversed()
        }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    var isLoading by mutableStateOf(false)
    var orderItemsList: List<Product>? by mutableStateOf(null)
    var orderItemsMap: Map<String, String>? by mutableStateOf(null)
    fun refreshOrders() {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading = true
            orderRepository.refreshOrders()
            isLoading = false
        }
    }

    fun getOrderItems(itemsMap: Map<String, String>) {
        viewModelScope.launch(Dispatchers.IO) {
            orderItemsMap = itemsMap
            orderItemsList = productRepository.getProductsInCart(itemsMap.keys).firstOrNull()
        }
    }

    fun resetOrderItems() {
        orderItemsList = null
        orderItemsMap = null
    }
}