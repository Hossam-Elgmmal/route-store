package com.route.ecommerce.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.route.ecommerce.ui.EcomAppState
import com.route.ecommerce.ui.screens.AccountScreen
import com.route.ecommerce.ui.screens.CartScreen
import com.route.ecommerce.ui.screens.CategoriesScreen
import com.route.ecommerce.ui.screens.HomeScreen
import com.route.ecommerce.ui.screens.LoginScreen
import com.route.ecommerce.ui.screens.ProductDetailsScreen
import com.route.ecommerce.ui.screens.ProductsScreen
import com.route.ecommerce.ui.screens.SignupScreen
import com.route.ecommerce.ui.screens.WishlistScreen

enum class Destinations {
    HOME,
    LOGIN,
    SIGNUP,
    CATEGORIES,
    CART,
    ACCOUNT,
    PRODUCTS,
    PRODUCT_DETAILS,
    WISHLIST,
}

fun NavController.navigateToHome(navOptions: NavOptions? = null) =
    navigate(Destinations.HOME.name, navOptions)

fun NavController.navigateToLogin(navOptions: NavOptions? = null) =
    navigate(Destinations.LOGIN.name, navOptions)

fun NavController.navigateToSignup(navOptions: NavOptions? = null) =
    navigate(Destinations.SIGNUP.name, navOptions)

fun NavController.navigateToCategories(navOptions: NavOptions? = null) =
    navigate(Destinations.CATEGORIES.name, navOptions)

fun NavController.navigateToCart(navOptions: NavOptions? = null) =
    navigate(Destinations.CART.name, navOptions)

fun NavController.navigateToAccount(navOptions: NavOptions? = null) =
    navigate(Destinations.ACCOUNT.name, navOptions)

fun NavController.navigateToProductDetails(navOptions: NavOptions? = null) =
    navigate(Destinations.PRODUCT_DETAILS.name, navOptions)

fun NavController.navigateToProducts(navOptions: NavOptions? = null) =
    navigate(Destinations.PRODUCTS.name, navOptions)

fun NavController.navigateToWishlist(navOptions: NavOptions? = null) =
    navigate(Destinations.WISHLIST.name, navOptions)

fun NavGraphBuilder.homeScreen(
    appState: EcomAppState
) {
    composable(route = Destinations.HOME.name) {
        HomeScreen(
            appState = appState
        )
    }
}

fun NavGraphBuilder.loginScreen() {
    composable(route = Destinations.LOGIN.name) {
        LoginScreen()
    }
}

fun NavGraphBuilder.signupScreen() {
    composable(route = Destinations.SIGNUP.name) {
        SignupScreen()
    }
}

fun NavGraphBuilder.categoriesScreen() {
    composable(Destinations.CATEGORIES.name) {
        CategoriesScreen()
    }
}

fun NavGraphBuilder.cartScreen() {
    composable(Destinations.CART.name) {
        CartScreen()
    }
}

fun NavGraphBuilder.accountScreen() {
    composable(Destinations.ACCOUNT.name) {
        AccountScreen()
    }
}

fun NavGraphBuilder.productDetailsScreen() {
    composable(Destinations.PRODUCT_DETAILS.name) {
        ProductDetailsScreen()
    }
}

fun NavGraphBuilder.productsScreen() {
    composable(Destinations.PRODUCTS.name) {
        ProductsScreen()
    }
}

fun NavGraphBuilder.wishlistScreen() {
    composable(Destinations.WISHLIST.name) {
        WishlistScreen()
    }
}