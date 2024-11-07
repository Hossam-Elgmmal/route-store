package com.route.ecommerce.ui.screens.orders

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.route.ecommerce.R
import com.route.ecommerce.ui.EcomAppState

@Composable
fun OrdersScreen(
    appState: EcomAppState,
    modifier: Modifier = Modifier,
    viewModel: OrdersViewModel = hiltViewModel()
) {
    val userOrders by viewModel.userOrders.collectAsState()
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        items(userOrders) { order ->
            Text(order.toString())
        }
        if (userOrders.isEmpty()) {
            item {
                Text(stringResource(R.string.orders_not_found))
            }
        }
        item {
            TextButton(
                onClick = { viewModel.refreshOrders() }
            ) {
                Text(text = stringResource(R.string.refresh))
            }
        }
    }
}