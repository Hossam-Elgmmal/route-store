package com.route.ecommerce.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.route.data.model.Product
import com.route.ecommerce.R

@Composable
fun ProductData(
    product: Product,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = product.title,
            style = MaterialTheme.typography.titleLarge
        )
        RatingRow(
            rating = product.ratingsAverage,
            ratingQuantity = product.ratingsQuantity,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        if (product.quantity <= 10) {
            Text(
                text = stringResource(R.string.in_stock_order_now, product.quantity),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.error
            )
        } else {
            Text(
                text = stringResource(id = R.string.in_stock),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Normal,
            )
        }
        HorizontalDivider()
        Text(
            text = stringResource(R.string.about_this_item),
            style = MaterialTheme.typography.labelLarge,
        )
        Text(
            text = product.description,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Composable
fun AddRemoveCard(
    onMinusClick: (String) -> Unit,
    onPlusClick: (String) -> Unit,
    countInCart: Int,
    product: Product,
    addToCart: (String, Int) -> Unit,
    modifier: Modifier = Modifier,
    onCountClick: () -> Unit = {},
) {
    ElevatedCard(
        shape = MaterialTheme.shapes.extraSmall,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically

        ) {
            PriceText(
                price = product.price
            )
            Surface(
                modifier = modifier,
                shape = CircleShape,
                color = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Row {
                    IconButton(
                        onClick = { onMinusClick(product.id) },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_minus),
                            contentDescription = stringResource(R.string.quantity_minus_one)
                        )
                    }
                    IconButton(
                        onClick = onCountClick,
                    ) {
                        Text(text = countInCart.toString())
                    }
                    IconButton(
                        onClick = { onPlusClick(product.id) },
                        enabled = countInCart < product.quantity,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = stringResource(R.string.quantity_plus_one)
                        )
                    }
                }
            }
        }
        Button(
            onClick = {
                addToCart(product.id, countInCart)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            enabled = product.quantity > countInCart
        ) {
            Icon(painter = painterResource(R.drawable.ic_cart), contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.add_to_cart),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}