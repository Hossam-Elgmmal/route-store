package com.route.data.reposetory

import com.route.data.Syncable
import com.route.data.Synchronizer
import com.route.data.dataVersionSync
import com.route.data.model.CartProduct
import com.route.database.dao.CartProductDao
import com.route.database.model.CartProductEntity
import com.route.datastore.DataVersion
import com.route.datastore.UserPreferencesRepository
import com.route.network.model.NetworkCartProduct
import com.route.network.model.NetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val cartProductDao: CartProductDao,
    private val userPreferencesRepository: UserPreferencesRepository,
) : CartRepository {

    private val token = userPreferencesRepository.getToken()

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean =
        synchronizer.dataVersionSync(
            versionReader = DataVersion::cartVersion,
            fetchDataList = { _ ->
                val t = token.firstOrNull() ?: ""
                val cart = networkRepository.getUserCart(t)
                userPreferencesRepository.setCartId(cart.cartId)
                cart.products
            },
            updateModelData = { cartProducts ->
                val list = cartProducts.map(NetworkCartProduct::asEntity)
                cartProductDao.updateCartProducts(list)
            },
            versionUpdater = { latestVersion ->
                copy(cartVersion = latestVersion)
            }
        )

    override suspend fun addCartProduct(productId: String) {
        cartProductDao.addCartProduct(CartProductEntity(productId, 1))
        val t = token.firstOrNull() ?: ""
        if (t != "") {
            networkRepository.addProductToCart(t, productId)
        }
    }

    override suspend fun updateCartProduct(productId: String, count: Int) {
        if (count > 0) {
            cartProductDao.addCartProduct(CartProductEntity(productId, count))
            val t = token.firstOrNull() ?: ""
            if (t != "") {
                networkRepository.updateProductCount(t, count, productId)
            }
        } else {
            removeCartProduct(productId)
        }
    }

    override suspend fun plusOneCartProduct(productId: String) {
        val newCount = cartProductDao.getProductCount(productId) + 1
        updateCartProduct(productId, newCount)
    }

    override suspend fun minusOneCartProduct(productId: String) {
        val newCount = cartProductDao.getProductCount(productId) - 1
        if (newCount <= 0) {
            removeCartProduct(productId)
        } else {
            updateCartProduct(productId, newCount)
        }
    }

    override fun getCartProducts() =
        cartProductDao.getCartProducts().map {
            it.map(CartProductEntity::asExternalModel)
        }

    override suspend fun removeCartProduct(productId: String) {
        cartProductDao.removeCartProduct(productId)
        val t = token.firstOrNull() ?: ""
        if (t != "") {
            networkRepository.removeCartItem(t, productId)
        }
    }
}

interface CartRepository : Syncable {
    fun getCartProducts(): Flow<List<CartProduct>>
    suspend fun addCartProduct(productId: String)
    suspend fun updateCartProduct(productId: String, count: Int)
    suspend fun plusOneCartProduct(productId: String)
    suspend fun minusOneCartProduct(productId: String)
    suspend fun removeCartProduct(productId: String)
}

fun NetworkCartProduct.asEntity() = CartProductEntity(
    count = count,
    id = product.id
)

fun CartProductEntity.asExternalModel() = CartProduct(
    id = id,
    count = count,
)