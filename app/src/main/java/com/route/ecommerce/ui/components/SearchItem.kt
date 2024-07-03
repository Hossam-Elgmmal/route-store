package com.route.ecommerce.ui.components

import android.icu.text.NumberFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
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
import java.util.Locale

@Composable
fun SearchItem(
    product: Product,
    onItemClick: () -> Unit,
    addToCart: (String) -> Unit,
    removeFromCart: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    var canRemoveItem by remember { mutableStateOf(false) }

    val imageLoader = rememberAsyncImagePainter(
        model = product.imageCoverUrl,
        onState = { state ->
            isLoading = state is AsyncImagePainter.State.Loading
            isError = state is AsyncImagePainter.State.Error
        }
    )
    val locale = Locale.getDefault()
    val formatter = NumberFormat.getCurrencyInstance(locale)
    Surface(
        onClick = onItemClick,
        modifier = modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = if (!isError) imageLoader else painterResource(id = R.drawable.ic_error),
                contentDescription = null,
                modifier = Modifier
                    .size(width = 140.dp, height = 200.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant,
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

                RatingRow(
                    rating = product.ratingsAverage,
                    ratingQuantity = product.ratingsQuantity
                )
                Text(
                    text = formatter.format(product.price),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.headlineSmall,
                )
                if (product.quantity <= 10) {
                    Text(
                        text = stringResource(R.string.in_stock, product.quantity),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                Button(
                    onClick = {
                        addToCart(product.id)
                        canRemoveItem = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = product.quantity > 0
                ) {
                    Text(text = stringResource(R.string.add_to_cart))
                }
                if (canRemoveItem) {
                    Row(
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.in_basket),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = stringResource(R.string.remove),
                            modifier = Modifier
                                .clickable {
                                    removeFromCart(product.id)
                                    canRemoveItem = false
                                },
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}