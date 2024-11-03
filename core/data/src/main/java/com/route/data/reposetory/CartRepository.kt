package com.route.data.reposetory

import com.route.data.model.CartProduct
import com.route.database.dao.CartProductDao
import com.route.database.model.CartProductEntity
import com.route.network.NetworkRepository
import com.route.network.model.Cart
import com.route.network.model.NetworkCartProduct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val cartProductDao: CartProductDao,
) : CartRepository {

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

    override suspend fun uploadCart(
        cartSet: Set<CartProduct>,
        token: String
    ): Cart = withContext(Dispatchers.IO) {

        val onlineCart = networkRepository.getUserCart(token)
        val onlineCartSet = onlineCart.products
            .map(NetworkCartProduct::asEntity)
            .map(CartProductEntity::asExternalModel)
            .toSet()

        if (cartSet == onlineCartSet) return@withContext onlineCart

        val newSet = cartSet - onlineCartSet
        val deletedSet = onlineCartSet - cartSet

        val itemsDeletedSuccessfully =
            deletedSet.map { cartItem ->
                async {
                    networkRepository.removeCartItem(token, cartItem.id)
                }
            }.awaitAll().all { it }

        val itemsAddedSuccessfully =
            newSet.map { cartItem ->
                async {
                    networkRepository.addProductToCart(token, cartItem.id)
                }
            }.awaitAll().all { it }

        val itemsUpdatedSuccessfully =
            newSet.filter { it.count > 1 }
                .map { cartItem ->
                    async {
                        networkRepository.updateProductCount(
                            token,
                            cartItem.count,
                            cartItem.id
                        )
                    }
                }.awaitAll().all { it }

        return@withContext if (itemsDeletedSuccessfully && itemsAddedSuccessfully && itemsUpdatedSuccessfully)
            networkRepository.getUserCart(token)
        else Cart("", "", emptyList())
    }
}

interface CartRepository {
    fun getCartProducts(): Flow<List<CartProduct>>
    suspend fun upsertCartProduct(productId: String, count: Int)
    suspend fun uploadCart(cartSet: Set<CartProduct>, token: String): Cart
}

fun NetworkCartProduct.asEntity() = CartProductEntity(
    count = count,
    id = product.id
)

fun CartProductEntity.asExternalModel() = CartProduct(
    id = id,
    count = count,
)