package com.route.ecommerce.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.route.data.model.Product
import com.route.ecommerce.R

@Composable
fun CartItem(
    product: Product,
    countInCart: Int,
    onCountClick: () -> Unit,
    onItemClick: () -> Unit,
    onPlusClick: (String) -> Unit,
    onMinusClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isError by remember { mutableStateOf(false) }

    val imageLoader = rememberAsyncImagePainter(
        model = product.imageCoverUrl,
        onState = { state ->
            isError = state is AsyncImagePainter.State.Error
        }
    )
    Surface(
        onClick = onItemClick,
        modifier = modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row {
                Image(
                    painter = if (!isError) imageLoader else painterResource(id = R.drawable.ic_error),
                    contentDescription = null,
                    modifier = Modifier
                        .size(width = 140.dp, height = 200.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outline,
                            shape = MaterialTheme.shapes.medium
                        )
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = product.title,
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    PriceText(price = product.price)
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
                    Text(
                        text = product.description,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
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
                IconButton(
                    onClick = { onDeleteClick(product.id) },
                    modifier = Modifier
                        .border(1.dp, MaterialTheme.colorScheme.error, CircleShape)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = stringResource(id = R.string.delete),
                        tint = MaterialTheme.colorScheme.error,
                    )
                }
            }
        }
    }
}