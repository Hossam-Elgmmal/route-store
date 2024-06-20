package com.route.ecommerce.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.route.ecommerce.navigation.TopLevelDestination

@Composable
fun EcomNavigationBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination, Boolean) -> Unit,
    latestTopLevelDestination: TopLevelDestination,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
        tonalElevation = 0.dp
    ) {
        destinations.forEach { destination ->
            val selected = latestTopLevelDestination == destination
            EcomNavigationBarItem(
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
    alwaysShowLabel: Boolean = true,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit = icon,
    label: @Composable () -> Unit
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        alwaysShowLabel = alwaysShowLabel,
        label = label,
        modifier = modifier
    )
}