package com.route.ecommerce.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.route.ecommerce.navigation.TopLevelDestination

@Composable
fun EcomNavigationBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination, Boolean) -> Unit,
    latestTopLevelDestination: TopLevelDestination,
    cartCount: Int,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
        tonalElevation = 0.dp
    ) {
        destinations.forEach { destination ->
            val selected = latestTopLevelDestination == destination
            val iconId = if (selected) destination.selectedIconId else destination.iconId
            EcomNavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination, selected) },
                icon = {
                    if (destination == TopLevelDestination.CART) {
                        EcomBadgedIcon(iconId = iconId, badgeText = cartCount.toString())
                    } else {
                        Icon(
                            painter = painterResource(id = iconId), contentDescription = null
                        )
                    }
                },
                label = {
                    Text(
                        text = stringResource(id = destination.iconTextId),
                    )
                }
            )
        }
    }
}

@Composable
fun RowScope.EcomNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    label: @Composable () -> Unit
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = icon,
        label = label,
        modifier = modifier
    )
}

@Composable
fun EcomBadgedIcon(
    @DrawableRes iconId: Int,
    badgeText: String,
    modifier: Modifier = Modifier
) {
    BadgedBox(
        badge = {
            if (badgeText.isNotEmpty()) {
                Badge(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ) { Text(text = badgeText) }
            }
        },
        modifier = modifier
    ) {
        Icon(painter = painterResource(iconId), contentDescription = null)
    }
}