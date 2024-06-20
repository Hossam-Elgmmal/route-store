package com.route.ecommerce.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.route.ecommerce.navigation.TopLevelDestination

@Composable
fun EcomNavRail(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination, Boolean) -> Unit,
    latestTopLevelDestination: TopLevelDestination,
    modifier: Modifier = Modifier
) {
    NavigationRail(
        containerColor = Color.Transparent,
        modifier = modifier
    ) {
        destinations.forEach { destination ->
            val selected = latestTopLevelDestination == destination
            EcomNavRailItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination, selected) },
                icon = {
                    Icon(
                        painter = painterResource(id = destination.iconId),
                        contentDescription = null
                    )
                },
                selectedIcon = {
                    Icon(
                        painter = painterResource(id = destination.selectedIconId),
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = destination.iconTextId),
                        fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal
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
    alwaysShowLabel: Boolean = true,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit = icon,
    label: @Composable () -> Unit
) {
    NavigationRailItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        label = label,
        alwaysShowLabel = alwaysShowLabel
    )
}