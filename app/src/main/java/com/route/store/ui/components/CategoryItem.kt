package com.route.store.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.route.data.model.Category
import com.route.store.R

@Composable
fun CategoryItem(
    category: Category,
    onCardClick: (brandId: String, categoryId: String) -> Unit,
    shape: Shape,
    modifier: Modifier = Modifier
) {
    var isLoading by remember {
        mutableStateOf(true)
    }
    var isError by remember {
        mutableStateOf(false)
    }
    val imageLoader = rememberAsyncImagePainter(
        model = category.imageUrl,
        onState = { state ->
            isLoading = state is AsyncImagePainter.State.Loading
            isError = state is AsyncImagePainter.State.Error
        }
    )

    Card(
        onClick = { onCardClick("", category.id) },
        shape = shape,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Image(
                painter = if (!isError) imageLoader else painterResource(id = R.drawable.ic_error),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop,
                alpha = 0.15f
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(modifier = Modifier.width(20.dp))
                Image(
                    painter = if (!isError) imageLoader else painterResource(id = R.drawable.ic_error),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .shadow(1.dp, CircleShape)
                        .clip(CircleShape)
                        .size(120.dp)
                        .background(MaterialTheme.colorScheme.surfaceContainerLow),
                )
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}