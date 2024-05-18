package com.route.ecommerce.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.route.ecommerce.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcomTopBar(
    onNavigateToSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Image(
                painter = painterResource(id = R.drawable.img_logo),
                contentDescription = null,
            )
        },
        actions = {
            IconButton(onClick = onNavigateToSearch) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = stringResource(id = R.string.search)
                )
            }
        },

        modifier = modifier
    )
}