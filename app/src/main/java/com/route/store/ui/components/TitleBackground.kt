package com.route.store.ui.components

import androidx.compose.foundation.layout.Box
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
fun TitleBackground(
    // not used
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val currentTopColor = MaterialTheme.colorScheme.primaryContainer
    val currentBottomColor = MaterialTheme.colorScheme.inverseOnSurface
    Surface(
        modifier = modifier,
    ) {
        Box(
            Modifier
                .drawWithCache {
                    val offset = size.width * tan(
                        Math
                            .toRadians(11.06)
                            .toFloat(),
                    )
                    val start = Offset(0f, size.height / 2 + offset / 2)
                    val end = Offset(size.width, size.height / 2 - offset / 2)
                    val leftGradient = Brush.linearGradient(
                        0f to currentTopColor,
                        0.724f to Color.Transparent,
                        start = start,
                        end = end,
                    )
                    val rightGradient = Brush.linearGradient(
                        0.2552f to Color.Transparent,
                        1f to currentBottomColor,
                        start = start,
                        end = end,
                    )
                    onDrawBehind {
                        drawRect(leftGradient)
                        drawRect(rightGradient)
                    }
                },
        ) {
            content()
        }
    }
}