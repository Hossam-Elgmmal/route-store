package com.route.ecommerce.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.route.data.NetworkMonitor
import com.route.data.model.CartProduct
import com.route.ecommerce.navigation.LowLevelDestination
import com.route.ecommerce.navigation.TopLevelDestination
import com.route.ecommerce.navigation.navigateToAccount
import com.route.ecommerce.navigation.navigateToCart
import com.route.ecommerce.navigation.navigateToCheckout
import com.route.ecommerce.navigation.navigateToHome
import com.route.ecommerce.navigation.navigateToMenu
import com.route.ecommerce.navigation.navigateToProductDetails
import com.route.ecommerce.navigation.navigateToProducts
import com.route.ecommerce.navigation.navigateToSearch
import com.route.ecommerce.ui.auth.FORGOT_PASSWORD_ROUTE
import com.route.ecommerce.ui.auth.LOGIN_ROUTE
import com.route.ecommerce.ui.auth.SIGNUP_ROUTE
import com.route.ecommerce.ui.auth.navigateToForgotPassword
import com.route.ecommerce.ui.auth.navigateToLogin
import com.route.ecommerce.ui.auth.navigateToSignup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


@Composable
fun rememberEcomAppState(
    windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
    cartProductList: Flow<List<CartProduct>>,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): EcomAppState {
    return remember(
        windowSizeClass,
        navController,
        cartProductList,
        networkMonitor,
        coroutineScope
    ) {
        EcomAppState(
            windowSizeClass = windowSizeClass,
            networkMonitor = networkMonitor,
            cartProductList = cartProductList,
            navController = navController,
            coroutineScope = coroutineScope
        )
    }
}

class EcomAppState(
    val windowSizeClass: WindowSizeClass,
    val navController: NavHostController,
    cartProductList: Flow<List<CartProduct>>,
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope,
) {
    private val currentDestination: NavDestination?
        @Composable
        get() = navController.currentBackStackEntryAsState().value?.destination

    private val currentTopLevelDestination: TopLevelDestination?
        @Composable
        get() = when (currentDestination?.route) {
            TopLevelDestination.HOME.name -> TopLevelDestination.HOME
            TopLevelDestination.MENU.name -> TopLevelDestination.MENU
            TopLevelDestination.CART.name -> TopLevelDestination.CART
            TopLevelDestination.ACCOUNT.name -> TopLevelDestination.ACCOUNT
            else -> null
        }
    val canNavigateUp: Boolean
        @Composable get() = currentTopLevelDestination == null
                && navController.previousBackStackEntry != null

    val canGoToSearch: Boolean
        @Composable get() = when (currentDestination?.route) {
            TopLevelDestination.ACCOUNT.name -> false
            LowLevelDestination.CHECKOUT.name -> false
            LowLevelDestination.SEARCH.name -> false
            LOGIN_ROUTE -> false
            SIGNUP_ROUTE -> false
            FORGOT_PASSWORD_ROUTE -> false
            else -> true
        }

    val canShowSettings: Boolean
        @Composable get() = currentDestination?.route == TopLevelDestination.ACCOUNT.name

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    val shouldShowBottomBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    val shouldShowNavRail: Boolean
        get() = !shouldShowBottomBar

    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

    val cartMap: StateFlow<Map<String, Int>> =
        cartProductList.map { list ->
            list.associate { it.id to it.count }
        }.stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyMap(),
        )

    fun navigateToTopLevelDestinations(
        destination: TopLevelDestination,
        selected: Boolean,
    ) {
        val topLevelNavOptions = if (destination == TopLevelDestination.HOME && selected)
            navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    inclusive = true
                    saveState = false
                }
                launchSingleTop = false
                restoreState = false
            }
        else
            navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = !selected
                }
                launchSingleTop = true
                restoreState = !selected
            }

        when (destination) {
            TopLevelDestination.HOME -> navController.navigateToHome(topLevelNavOptions)
            TopLevelDestination.MENU -> navController.navigateToMenu(topLevelNavOptions)
            TopLevelDestination.CART -> navController.navigateToCart(topLevelNavOptions)
            TopLevelDestination.ACCOUNT -> navController.navigateToAccount(topLevelNavOptions)
        }
    }

    fun navigateToLogin() = navController.navigateToLogin()
    fun navigateToSignup() = navController.navigateToSignup()
    fun navigateToForgotPassword() = navController.navigateToForgotPassword()
    fun navigateToProducts(brandId: String, categoryId: String) =
        navController.navigateToProducts(brandId = brandId, categoryId = categoryId)
    fun navigateToProductDetails(id: String) = navController.navigateToProductDetails(id)
    fun navigateToCheckout(cartId: String) = navController.navigateToCheckout(cartId)
    fun navigateToSearch() = navController.navigateToSearch()
    fun navigateUp() = navController.navigateUp()
    fun popBackStack() = navController.popBackStack()

}