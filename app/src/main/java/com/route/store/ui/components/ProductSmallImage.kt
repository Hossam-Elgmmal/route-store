package com.route.store.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.route.store.R

@Composable
fun ProductSmallImage(
    url: String,
    onImageClick: (String) -> Unit,
    outline: Boolean,
    modifier: Modifier = Modifier,
) {
    var isError by remember {
        mutableStateOf(false)
    }
    val imageLoader = rememberAsyncImagePainter(
        model = url,
        onState = { state ->
            isError = state is AsyncImagePainter.State.Error
        }
    )
    OutlinedCard(
        shape = MaterialTheme.shapes.extraSmall,
        border = if (outline) {
            BorderStroke(2.dp, MaterialTheme.colorScheme.outline)
        } else {
            CardDefaults.outlinedCardBorder()
        }
    ) {
        Image(
            painter = if (!isError) imageLoader else painterResource(id = R.drawable.ic_error),
            contentDescription = null,
            modifier = modifier
                .height(80.dp)
                .width(58.dp)
                .clickable {
                    onImageClick(url)
                },
            contentScale = ContentScale.Fit,
        )
    }
}