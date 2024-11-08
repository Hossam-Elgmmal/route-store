package com.route.ecommerce.ui.screens.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.route.data.model.Product
import com.route.ecommerce.R
import com.route.ecommerce.ui.components.PriceText

@Composable
fun SubtotalBody(
    subtotal: Int,
    itemsCount: Int,
    onCheckout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row {
            Text(
                text = stringResource(R.string.subtotal),
                style = MaterialTheme.typography.headlineSmall
            )
            PriceText(price = subtotal)
        }
        Button(
            onClick = onCheckout,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            shape = CircleShape
        ) {
            Text(
                text = stringResource(R.string.proceed_to_buy_items, itemsCount),
            )
        }
    }
}

fun calculateSubTotal(cartProductsMap: Map<String, Int>, products: List<Product>): Int {
    return products.mapNotNull { product ->
        cartProductsMap[product.id]?.let { count ->
            count * product.price
        }
    }.sum()
}