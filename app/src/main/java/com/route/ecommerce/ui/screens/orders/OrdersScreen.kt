package com.route.ecommerce.ui.screens.orders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.route.ecommerce.R
import com.route.ecommerce.ui.EcomAppState
import com.route.ecommerce.ui.components.LoadingDialog

@Composable
fun OrdersScreen(
    appState: EcomAppState,
    modifier: Modifier = Modifier,
    viewModel: OrdersViewModel = hiltViewModel()
) {
    val userOrders by viewModel.userOrders.collectAsState()
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.all_orders),
                    style = MaterialTheme.typography.labelLarge.copy(fontSize = 18.sp)
                )
                TextButton(
                    onClick = { viewModel.refreshOrders() },
                ) {
                    Text(text = stringResource(R.string.refresh))
                }
            }
        }
        items(userOrders, key = { it.id }) { order ->
            OutlinedCard(
                shape = MaterialTheme.shapes.extraSmall
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    OrderDetailRow(R.string.address, order.address)
                    OrderDetailRow(R.string.city, order.city)
                    OrderDetailRow(R.string.phone_number, order.phone)
                    OrderDetailRow(R.string.createdAt, order.createdAt.dropLast(14))
                    HorizontalDivider()
                    OrderDetailRow(R.string.payment_method, order.paymentMethodType)
                    OrderDetailRow(
                        R.string.total,
                        order.totalOrderPrice.toString() + " EGP"
                    ) // sometimes the backend sends wrong total
                    HorizontalDivider()
                    OrderDetailRow(
                        R.string.delivered,
                        if (order.isDelivered) stringResource(R.string.yes) else stringResource(
                            R.string.no
                        )
                    )
                    OrderDetailRow(
                        R.string.paid,
                        if (order.isPaid) stringResource(R.string.yes) else stringResource(
                            R.string.no
                        )
                    )
                    HorizontalDivider()
                    OrderDetailRow(
                        R.string.items,
                        order.cartItems.values.sumOf { it.toInt() }.toString()
                    )
                    Button(
                        onClick = {
                            viewModel.getOrderItems(order.cartItems)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.show_items)
                        )
                    }
                }
            }
        }

        if (userOrders.isEmpty()) {
            item {
                Text(stringResource(R.string.orders_not_found))
            }
        }
    }
    if (viewModel.isLoading) {
        LoadingDialog()
    }

    viewModel.orderItemsList?.let { items ->
        AlertDialog(
            properties = DialogProperties(usePlatformDefaultWidth = false),
            modifier = Modifier.fillMaxWidth(0.9f),
            onDismissRequest = {
                viewModel.resetOrderItems()
            },
            confirmButton = {
                Button(
                    onClick = { viewModel.resetOrderItems() }
                ) {
                    Text(text = stringResource(R.string.ok))
                }
            },
            title = {
                Text(
                    text = stringResource(R.string.order_items),
                )
            },
            text = {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items) { product ->
                        SmallProduct(product, viewModel.orderItemsMap?.get(product.id) ?: "")
                    }
                }
            }
        )
    }
}
