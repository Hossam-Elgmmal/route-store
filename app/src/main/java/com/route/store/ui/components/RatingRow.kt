package com.route.store.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.route.store.R
import com.route.store.ui.theme.ratingColor

@Composable
fun RatingRow(
    rating: Double,
    ratingQuantity: Int,
    modifier: Modifier = Modifier,
) {
    val fullStars = rating.toInt()
    val fraction = rating % 1
    var remainingStars = 5
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = rating.toString(),
            style = MaterialTheme.typography.bodySmall
        )
        for (i in 1..fullStars) {
            remainingStars--
            Icon(
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = null,
                tint = ratingColor
            )
        }
        if (fraction in 0.25..0.75) {
            remainingStars--
            Icon(
                painter = painterResource(id = R.drawable.ic_half_star),
                contentDescription = null,
                tint = ratingColor
            )
        } else if (fraction > 0.75) {
            remainingStars--
            Icon(
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = null,
                tint = ratingColor
            )
        }
        for (i in 1..remainingStars) {
            Icon(
                painter = painterResource(id = R.drawable.ic_empty_star),
                contentDescription = null,
                tint = ratingColor
            )
        }
        Text(
            text = "($ratingQuantity)",
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.bodySmall
        )
    }
}
