package com.route.ecommerce.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.route.data.model.Brand
import com.route.ecommerce.R

@Composable
fun HomeBrandsRow(
    onItemClick: (brandId: String, categoryId: String) -> Unit,
    brandsList: List<Brand>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 4.dp
        )
        Text(
            text = stringResource(R.string.brands),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .graphicsLayer(
                    scaleX = 1.1f,
                )
        )
        LazyRow(
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                brandsList
            ) { brand ->
                HomeBrand(
                    onItemClick = onItemClick,
                    brand = brand
                )
            }
        }
    }
}

@Composable
fun HomeBrand(
    onItemClick: (brandId: String, categoryId: String) -> Unit,
    brand: Brand,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        onClick = { onItemClick(brand.id, "") },
        modifier = modifier,
        shape = MaterialTheme.shapes.extraSmall
    ) {
        AsyncImage(
            model = brand.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .width(180.dp)
                .height(140.dp),
            contentScale = ContentScale.Crop
        )
    }
}