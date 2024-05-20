package com.route.ecommerce.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.route.ecommerce.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcomTopBar(
    canGoToSearch: Boolean,
    onNavigateToSearch: () -> Unit,
    canNavigateUp: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        navigationIcon = {
            if (canNavigateUp) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            }
        },
        title = {
            Image(
                painter = painterResource(id = R.drawable.ic_app_bar_logo),
                contentDescription = null,
            )
        },
        actions = {
            if (canGoToSearch) {
                IconButton(onClick = onNavigateToSearch) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = stringResource(id = R.string.search)
                    )
                }
            }
        },

        modifier = modifier
    )
}