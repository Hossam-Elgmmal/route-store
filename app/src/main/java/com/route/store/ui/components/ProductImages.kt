package com.route.store.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.route.data.model.Product

@Composable
fun ProductImages(
    product: Product,
    modifier: Modifier = Modifier,
) {
    var isError by remember {
        mutableStateOf(false)
    }
    var selectedUrl by rememberSaveable { mutableStateOf(product.imageCoverUrl) }
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier.shadow(4.dp, MaterialTheme.shapes.small)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
        ) {
            AnimatedContent(
                targetState = selectedUrl,
                label = "ProductImage",
                transitionSpec = {
                    (fadeIn(
                        animationSpec = tween(
                            220,
                            delayMillis = 90
                        )
                    )).togetherWith(fadeOut(animationSpec = tween(90)))
                }
            ) { url ->
                Image(
                    painter =
                    rememberAsyncImagePainter(
                        model = url,
                        onState = { state ->
                            isError = state is AsyncImagePainter.State.Error
                        }
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .height(360.dp)
                        .width(261.dp)
                )
            }

            var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(8.dp, 16.dp),
                modifier = Modifier.height(360.dp)
            ) {
                item {
                    ProductSmallImage(
                        url = product.imageCoverUrl,
                        onImageClick = {
                            selectedUrl = it
                            selectedIndex = 0
                        },
                        outline = selectedIndex == 0
                    )
                }
                itemsIndexed(product.imagesUrlList) { index, url ->
                    ProductSmallImage(
                        url = url,
                        onImageClick = {
                            selectedUrl = it
                            selectedIndex = index + 1
                        },
                        outline = selectedIndex == index + 1
                    )
                }
            }
        }
    }
}

