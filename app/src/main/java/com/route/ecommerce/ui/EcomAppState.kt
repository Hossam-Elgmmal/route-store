package com.route.ecommerce.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.route.ecommerce.navigation.LowLevelDestination
import com.route.ecommerce.navigation.TopLevelDestination
import com.route.ecommerce.navigation.navigateToAccount
import com.route.ecommerce.navigation.navigateToCart
import com.route.ecommerce.navigation.navigateToCategories
import com.route.ecommerce.navigation.navigateToHome
import com.route.ecommerce.navigation.navigateToLogin
import com.route.ecommerce.navigation.navigateToProductDetails
import com.route.ecommerce.navigation.navigateToProducts
import com.route.ecommerce.navigation.navigateToSearch
import com.route.ecommerce.navigation.navigateToSignup
import com.route.ecommerce.navigation.navigateToWishlist


@Composable
fun rememberEcomAppState(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberNavController()
): EcomAppState {
    return remember(
        windowSizeClass,
        navController
    ) {
        EcomAppState(
            windowSizeClass = windowSizeClass,
            navController = navController
        )
    }
}

class EcomAppState(
    val windowSizeClass: WindowSizeClass,
    val navController: NavHostController
) {
    val currentDestination: NavDestination?
        @Composable
        get() = navController.currentBackStackEntryAsState().value?.destination

    private val currentTopLevelDestination: TopLevelDestination?
        @Composable
        get() = when (currentDestination?.route) {
            TopLevelDestination.HOME.name -> TopLevelDestination.HOME
            TopLevelDestination.CATEGORIES.name -> TopLevelDestination.CATEGORIES
            TopLevelDestination.CART.name -> TopLevelDestination.CART
            TopLevelDestination.ACCOUNT.name -> TopLevelDestination.CART
            else -> null
        }
    val canNavigateUp: Boolean
        @Composable get() = currentTopLevelDestination == null
                && navController.previousBackStackEntry != null

    val canGoToSearch: Boolean
        @Composable get() = when (currentDestination?.route) {
            TopLevelDestination.ACCOUNT.name -> false
            LowLevelDestination.SEARCH.name -> false
            LowLevelDestination.LOGIN.name -> false
            LowLevelDestination.SIGNUP.name -> false
            else -> true
        }

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    val shouldShowTopBar: Boolean
        @Composable get() = currentTopLevelDestination != null
    val shouldShowBottomBar: Boolean
        @Composable get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
                && currentTopLevelDestination != null

    val shouldShowNavRail: Boolean
        @Composable get() = windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact
                && currentTopLevelDestination != null

    fun navigateToTopLevelDestinations(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        when (topLevelDestination) {
            TopLevelDestination.HOME -> navController.navigateToHome(topLevelNavOptions)
            TopLevelDestination.CATEGORIES -> navController.navigateToCategories(topLevelNavOptions)
            TopLevelDestination.CART -> navController.navigateToCart(topLevelNavOptions)
            TopLevelDestination.ACCOUNT -> navController.navigateToAccount(topLevelNavOptions)
        }
    }

    fun navigateToLogin() = navController.navigateToLogin()
    fun navigateToSignup() = navController.navigateToSignup()
    fun navigateToProducts() = navController.navigateToProducts()
    fun navigateToProductDetails() = navController.navigateToProductDetails()
    fun navigateToWishlist() = navController.navigateToWishlist()
    fun navigateToSearch() = navController.navigateToSearch()
    fun navigateUp() = navController.navigateUp()

}