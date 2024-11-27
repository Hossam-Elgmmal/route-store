package com.route.store.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.route.store.navigation.TopLevelDestination

@Composable
fun EcomNavRail(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination, Boolean) -> Unit,
    latestTopLevelDestination: TopLevelDestination,
    cartCount: Int,
    modifier: Modifier = Modifier
) {
    NavigationRail(
        containerColor = Color.Transparent,
        modifier = modifier
    ) {
        destinations.forEach { destination ->
            val selected = latestTopLevelDestination == destination
            val iconId = if (selected) destination.selectedIconId else destination.iconId
            EcomNavRailItem(
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
                },
            )
        }
    }
}

@Composable
fun EcomNavRailItem(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    label: @Composable () -> Unit
) {
    NavigationRailItem(
        selected = selected,
        onClick = onClick,
        icon = icon,
        modifier = modifier,
        label = label,
    )
}