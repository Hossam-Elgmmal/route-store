package com.route.store.ui.screens.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.route.store.R

@Composable
fun OfferCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    imgId: Int = R.drawable.img_bag
) {
    val gradientColor = MaterialTheme.colorScheme.primaryContainer
    Surface(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(240.dp)
            .padding(8.dp),
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.surfaceContainerHigh
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.bg_pattern),
                contentDescription = null,
                alpha = 0.3f,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Row {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.46f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        modifier = Modifier.align(Alignment.Start),
                        shape = RoundedCornerShape(16.dp, 0.dp, 16.dp, 0.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.today_s_deal),
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    Text(
                        text = stringResource(R.string.big_sale),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .graphicsLayer(
                                scaleX = 0.8f,
                                scaleY = 1.5f,
                                rotationX = 30f,
                            )
                    )
                    Text(
                        text = stringResource(id = R.string._50_off),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .graphicsLayer(
                                scaleX = 1.1f,
                                scaleY = 1.5f,
                                rotationX = 30f,
                                translationY = -10f
                            )
                    )
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.surfaceContainerLow,
                        contentColor = MaterialTheme.colorScheme.primary,
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp, 8.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.shop_now),
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_forward),
                                contentDescription = null
                            )
                        }
                    }
                }
                Surface(
                    shape = RoundedCornerShape(topStart = 500.dp, bottomStart = 500.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .drawWithCache {
                                val gradient = Brush.radialGradient(
                                    colors = listOf(
                                        gradientColor,
                                        Color.Transparent
                                    ),
                                    radius = 250f
                                )
                                onDrawBehind {
                                    drawRect(gradient)
                                }
                            }

                    ) {
                        AnimatedContent(
                            targetState = imgId,
                            label = "imgSale",
                            transitionSpec =
                            {
                                (fadeIn(
                                    animationSpec = tween(
                                        300,
                                        delayMillis = 90
                                    )
                                ) + slideInHorizontally()
                                        ).togetherWith(
                                        fadeOut(animationSpec = tween(90))
                                                + slideOutHorizontally { it / 2 }
                                    )
                            }

                        ) { id ->
                            Image(
                                painter = painterResource(id = id),
                                contentDescription = null,
                                modifier = Modifier.fillMaxHeight(0.8f)
                            )
                        }
                    }
                }
            }
        }
    }
}
