package com.route.store.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlin.math.tan

@Composable
fun EcomBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val currentTopColor = MaterialTheme.colorScheme.inverseOnSurface
    val currentBottomColor = MaterialTheme.colorScheme.primaryContainer
    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .drawWithCache {
                    val offset = size.height * tan(
                        Math
                            .toRadians(11.06)
                            .toFloat(),
                    )
                    val start = Offset(size.width / 2 + offset / 2, 0f)
                    val end = Offset(size.width / 2 - offset / 2, size.height)
                    val topGradient = Brush.linearGradient(
                        0f to currentTopColor,
                        0.724f to Color.Transparent,
                        start = start,
                        end = end,
                    )
                    val bottomGradient = Brush.linearGradient(
                        0.2552f to Color.Transparent,
                        1f to currentBottomColor,
                        start = start,
                        end = end,
                    )
                    onDrawBehind {
                        drawRect(topGradient)
                        drawRect(bottomGradient)
                    }
                },
        ) {
            content()
        }
    }
}