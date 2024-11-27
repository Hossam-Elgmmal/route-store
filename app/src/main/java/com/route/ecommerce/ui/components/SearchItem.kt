package com.route.ecommerce.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
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
import kotlinx.coroutines.CoroutineScope

@Composable
fun SearchItem(
    product: Product,
    countInCart: Int,
    upsertCartProduct: (String, Int) -> Unit,
    onItemClick: () -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
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
            .fillMaxWidth()
            .shadow(1.dp, MaterialTheme.shapes.small),
        shape = MaterialTheme.shapes.small,
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ElevatedCard(
                    shape = MaterialTheme.shapes.extraSmall,
                ) {
                    Image(
                        painter = if (!isError) imageLoader else painterResource(id = R.drawable.ic_error),
                        contentDescription = null,
                        modifier = Modifier
                            .size(width = 140.dp, height = 200.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = product.title,
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold
                    )

                    RatingRow(
                        rating = product.ratingsAverage,
                        ratingQuantity = product.ratingsQuantity,
                        modifier = Modifier.graphicsLayer {
                            scaleX = 0.9f
                            scaleY = 0.9f
                        }
                    )
                    PriceText(
                        price = product.price,
                        style = MaterialTheme.typography.titleLarge
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
                            text = stringResource(R.string.in_stock),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Normal
                        )
                    }
                    if (countInCart > 0) {
                        Row(
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Text(
                                text = countInCart.toString() + stringResource(R.string.in_basket),
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = stringResource(R.string.remove),
                                modifier = Modifier
                                    .clickable {
                                        onCartProductRemoved(
                                            coroutineScope = coroutineScope,
                                            snackbarHostState = snackbarHostState,
                                            countInCart = countInCart,
                                            onActionPerformed = {
                                                upsertCartProduct(product.id, countInCart)
                                            }
                                        )
                                        upsertCartProduct(product.id, 0)
                                    },
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
            Button(
                onClick = {
                    upsertCartProduct(product.id, countInCart + 1)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = product.quantity > countInCart
            ) {
                Text(text = stringResource(R.string.add_to_cart))
            }
        }
    }
}