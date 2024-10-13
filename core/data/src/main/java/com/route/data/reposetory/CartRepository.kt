package com.route.data.reposetory

import com.route.data.Syncable
import com.route.data.Synchronizer
import com.route.data.dataVersionSync
import com.route.data.model.CartProduct
import com.route.database.dao.CartProductDao
import com.route.database.model.CartProductEntity
import com.route.datastore.DataVersion
import com.route.datastore.UserPreferencesRepository
import com.route.network.NetworkRepository
import com.route.network.model.NetworkCartProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val cartProductDao: CartProductDao,
    private val userPreferencesRepository: UserPreferencesRepository,
) : CartRepository {

    private val token = userPreferencesRepository.getToken() /* TODO("use token in checkout") */

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

    override suspend fun upsertCartProduct(productId: String, count: Int) {
        if (count > 0) {
            cartProductDao.upsertCartProduct(CartProductEntity(productId, count))
        } else {
            cartProductDao.removeCartProduct(productId)
        }
    }

    override fun getCartProducts() =
        cartProductDao.getCartProducts().map {
            it.map(CartProductEntity::asExternalModel)
        }

}

interface CartRepository : Syncable {
    fun getCartProducts(): Flow<List<CartProduct>>
    suspend fun upsertCartProduct(productId: String, count: Int)
}

fun NetworkCartProduct.asEntity() = CartProductEntity(
    count = count,
    id = product.id
)

fun CartProductEntity.asExternalModel() = CartProduct(
    id = id,
    count = count,
)