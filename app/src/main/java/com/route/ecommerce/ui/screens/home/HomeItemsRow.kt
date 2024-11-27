package com.route.ecommerce.ui.screens.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.route.data.model.Product
import com.route.ecommerce.R
import com.route.ecommerce.ui.components.PriceText
import com.route.ecommerce.ui.theme.ratingColor

@Composable
fun HomeItemsRow(
    onItemClick: (String) -> Unit,
    @StringRes titleId: Int,
    modifier: Modifier = Modifier,
    productList: List<Product> = emptyList(),
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
        )
        Text(
            text = stringResource(titleId),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .graphicsLayer(
                    scaleX = 1.1f,
                )
        )

        LazyRow(
            modifier = Modifier.height(320.dp)
        ) {
            items(
                productList
            ) { product ->
                HomeItem(
                    onItemClick = onItemClick,
                    product = product
                )
            }
        }
    }
}

@Composable
fun HomeItem(
    onItemClick: (String) -> Unit,
    product: Product,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        onClick = { onItemClick(product.id) },
        modifier = modifier
            .padding(8.dp)
            .width(140.dp)
            .fillMaxHeight(),
        shape = MaterialTheme.shapes.extraSmall,
    ) {
        AsyncImage(
            model = product.imageCoverUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(MaterialTheme.shapes.extraSmall),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = product.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleSmall,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(start = 4.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = null,
                    tint = ratingColor
                )
                Text(
                    text = product.ratingsAverage.toString(),
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "(${product.ratingsQuantity})",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            PriceText(
                price = product.price,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}
