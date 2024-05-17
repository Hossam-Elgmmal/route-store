package com.route.ecommerce.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.route.ecommerce.navigation.Destinations
import com.route.ecommerce.navigation.navigateToAccount
import com.route.ecommerce.navigation.navigateToCart
import com.route.ecommerce.navigation.navigateToCategories
import com.route.ecommerce.navigation.navigateToHome
import com.route.ecommerce.navigation.navigateToLogin
import com.route.ecommerce.navigation.navigateToProductDetails
import com.route.ecommerce.navigation.navigateToProducts
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

    val currentTopLevelDestination: Destinations?
        @Composable
        get() = when (currentDestination?.route) {
            Destinations.HOME.name -> Destinations.HOME
            Destinations.CATEGORIES.name -> Destinations.CATEGORIES
            Destinations.WISHLIST.name -> Destinations.WISHLIST
            else -> null
        }
    val shouldShowAppBar: Boolean
        @Composable get() = currentTopLevelDestination != null
    val shouldShowBottomBar: Boolean
        @Composable get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
                && currentTopLevelDestination != null

    val shouldShowNavRail: Boolean
        @Composable get() = windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact
                && currentTopLevelDestination != null

    fun navigateToHome() = navController.navigateToHome()
    fun navigateToLogin() = navController.navigateToLogin()
    fun navigateToSignup() = navController.navigateToSignup()
    fun navigateToCategories() = navController.navigateToCategories()
    fun navigateToCart() = navController.navigateToCart()
    fun navigateToProducts() = navController.navigateToProducts()
    fun navigateToProductDetails() = navController.navigateToProductDetails()
    fun navigateToAccount() = navController.navigateToAccount()
    fun navigateToWishlist() = navController.navigateToWishlist()

}