package com.route.ecommerce.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.route.ecommerce.R
import com.route.ecommerce.ui.EcomAppState
import com.route.ecommerce.ui.screens.account.AccountScreen
import com.route.ecommerce.ui.screens.cart.CartScreen
import com.route.ecommerce.ui.screens.checkout.CheckoutScreen
import com.route.ecommerce.ui.screens.home.HomeScreen
import com.route.ecommerce.ui.screens.menu.MenuScreen
import com.route.ecommerce.ui.screens.productDetails.ProductDetailsScreen
import com.route.ecommerce.ui.screens.products.ProductsScreen
import com.route.ecommerce.ui.screens.search.SearchScreen

enum class TopLevelDestination(
    @DrawableRes val iconId: Int,
    @DrawableRes val selectedIconId: Int,
    @StringRes val iconTextId: Int,
) {
    HOME(
        iconId = R.drawable.ic_home,
        selectedIconId = R.drawable.ic_selected_home,
        iconTextId = R.string.home,
    ),
    MENU(
        iconId = R.drawable.ic_menu,
        selectedIconId = R.drawable.ic_selected_menu,
        iconTextId = R.string.menu,
    ),
    CART(
        iconId = R.drawable.ic_cart,
        selectedIconId = R.drawable.ic_selected_cart,
        iconTextId = R.string.cart,
    ),
    ACCOUNT(
        iconId = R.drawable.ic_account,
        selectedIconId = R.drawable.ic_selected_account,
        iconTextId = R.string.account,
    ),
}

enum class LowLevelDestination {
    WISHLIST,
    PRODUCTS,
    PRODUCT_DETAILS,
    SEARCH,
    CHECKOUT,
}

fun NavController.navigateToHome(navOptions: NavOptions? = null) =
    navigate(TopLevelDestination.HOME.name, navOptions)

fun NavController.navigateToMenu(navOptions: NavOptions? = null) =
    navigate(TopLevelDestination.MENU.name, navOptions)

fun NavController.navigateToAccount(navOptions: NavOptions? = null) =
    navigate(TopLevelDestination.ACCOUNT.name, navOptions)

fun NavController.navigateToCheckout(cartId: String, navOptions: NavOptions? = null) =
    navigate("${LowLevelDestination.CHECKOUT.name}/$cartId", navOptions)


fun NavController.navigateToCart(navOptions: NavOptions? = null) =
    navigate(TopLevelDestination.CART.name, navOptions)


fun NavController.navigateToProductDetails(id: String, navOptions: NavOptions? = null) =
    navigate("${LowLevelDestination.PRODUCT_DETAILS.name}/$id", navOptions)

fun NavController.navigateToProducts(
    brandId: String,
    categoryId: String,
    navOptions: NavOptions? = null
) =
    navigate(
        "${LowLevelDestination.PRODUCTS.name}?brand=$brandId?categoryId=$categoryId",
        navOptions
    )


fun NavController.navigateToSearch(navOptions: NavOptions? = null) =
    navigate(LowLevelDestination.SEARCH.name, navOptions)

fun NavGraphBuilder.homeScreen(
    appState: EcomAppState
) {
    composable(route = TopLevelDestination.HOME.name) {
        HomeScreen(
            appState = appState
        )
    }
}

fun NavGraphBuilder.menuScreen(
    appState: EcomAppState,
    onBackPressed: () -> Unit
) {
    composable(TopLevelDestination.MENU.name) {
        MenuScreen(
            appState = appState,
            onBackPressed = onBackPressed
        )
    }
}

fun NavGraphBuilder.accountScreen(
    appState: EcomAppState,
    onBackPressed: () -> Unit
) {
    composable(TopLevelDestination.ACCOUNT.name) {
        AccountScreen(
            appState = appState,
            onBackPressed = onBackPressed
        )
    }
}

fun NavGraphBuilder.cartScreen(
    appState: EcomAppState,
    onBackPressed: () -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    composable(TopLevelDestination.CART.name) {
        CartScreen(
            appState = appState,
            onBackPressed = onBackPressed,
            snackbarHostState = snackbarHostState,
        )
    }
}


private const val productId = "product_id"
fun NavGraphBuilder.productDetailsScreen(
    appState: EcomAppState,
    snackbarHostState: SnackbarHostState,
) {
    composable(
        route = "${LowLevelDestination.PRODUCT_DETAILS.name}/{$productId}",
        arguments = listOf(
            navArgument(productId) {
                type = NavType.StringType
            }
        )
    ) { backStackEntry ->
        val productId = backStackEntry.arguments?.getString(productId) ?: ""
        ProductDetailsScreen(
            appState = appState,
            productId = productId,
            snackbarHostState = snackbarHostState,
        )
    }
}

private const val brandId = "brand_id"
private const val categoryId = "category_id"
fun NavGraphBuilder.productsScreen(appState: EcomAppState) {
    composable(
        route = "${LowLevelDestination.PRODUCTS.name}?brand={$brandId}?categoryId={$categoryId}",
        arguments = listOf(
            navArgument(brandId) {
                type = NavType.StringType
            },
            navArgument(categoryId) {
                type = NavType.StringType
            },
        )
    ) { backStackEntry ->
        val brandId = backStackEntry.arguments?.getString(brandId) ?: ""
        val categoryId = backStackEntry.arguments?.getString(categoryId) ?: ""
        ProductsScreen(
            appState = appState,
            brandId = brandId,
            categoryId = categoryId,
        )
    }
}

fun NavGraphBuilder.searchScreen(
    appState: EcomAppState,
    cartItems: Map<String, Int>,
    snackbarHostState: SnackbarHostState,
) {
    composable(route = LowLevelDestination.SEARCH.name) {
        SearchScreen(
            appState = appState,
            cartItems = cartItems,
            snackbarHostState = snackbarHostState
        )
    }
}

private const val cartId = "cart_id"
fun NavGraphBuilder.checkoutScreen(appState: EcomAppState) {
    composable(
        route = "${LowLevelDestination.CHECKOUT.name}/{$cartId}",
        arguments = listOf(
            navArgument(cartId) {
                type = NavType.StringType
            }
        )
    ) { backStackEntry ->
        val cartId = backStackEntry.arguments?.getString(cartId) ?: ""
        CheckoutScreen(
            appState = appState,
            cartId = cartId,
        )
    }
}