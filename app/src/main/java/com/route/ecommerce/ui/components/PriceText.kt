package com.route.ecommerce.ui.components

import android.icu.text.NumberFormat
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import java.util.Locale


@Composable
fun PriceText(
    price: Int,
    modifier: Modifier = Modifier
) {
    val locale = Locale.getDefault()
    val formatter = NumberFormat.getCurrencyInstance(locale)
    Text(
        text = formatter.format(price),
        maxLines = 2,
        overflow = TextOverflow.Visible,
        style = MaterialTheme.typography.headlineSmall,
        modifier = modifier
    )
}