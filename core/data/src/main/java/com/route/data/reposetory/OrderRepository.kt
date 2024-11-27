package com.route.data.reposetory

import com.route.data.Syncable
import com.route.data.Synchronizer
import com.route.data.dataVersionSync
import com.route.data.model.CartProduct
import com.route.data.model.Order
import com.route.database.dao.OrderDao
import com.route.database.model.OrderEntity
import com.route.datastore.DataVersion
import com.route.datastore.UserPreferencesRepository
import com.route.network.NetworkRepository
import com.route.network.model.NetworkOrder
import com.route.network.model.OrderRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val orderDao: OrderDao,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : OrderRepository {

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean =
        synchronizer.dataVersionSync(
            versionReader = DataVersion::orderVersion,
            fetchDataList = { currentVersion ->
                if (currentVersion <= 0) {
                    val userId = userPreferencesRepository.getUserId()
                    if (userId.isNotEmpty()) {
                        networkRepository.getUserOrders(userId)
                    } else {
                        emptyList()
                    }
                } else {
                    emptyList()
                }
            },
            updateModelData = { ordersList ->
                if (ordersList.isNotEmpty()) {
                    val newList = ordersList.map(NetworkOrder::asEntity)
                    orderDao.addOrders(newList)
                }
            },
            versionUpdater = { latestVersion ->
                copy(productVersion = latestVersion)
            }
        )

    override suspend fun createCashOrder(
        token: String,
        cartId: String,
        request: OrderRequest
    ) = networkRepository.createCashOrder(token, cartId, request)

    override fun getOrders() =
        orderDao.getOrders().map {
            it.map(OrderEntity::asExternalModel)
        }

    override suspend fun refreshOrders() {
        val userId = userPreferencesRepository.getUserId()
        if (userId.isNotEmpty()) {
            getUserOrders(userId)
        } else {
            val productId = productRepository.getProducts().first()[0].id
            val cartProduct = CartProduct(productId, 1)
            val token = userPreferencesRepository.getToken()
            val onlineCart = cartRepository.uploadCart(setOf(cartProduct), token)
            userPreferencesRepository.setUserId(onlineCart.ownerId)
            getUserOrders(onlineCart.ownerId)
            cartRepository.uploadCart(emptySet(), token)
        }
    }

    private suspend fun getUserOrders(userId: String) {
        val ordersList = networkRepository.getUserOrders(userId)
        val newList = ordersList.map(NetworkOrder::asEntity)
        orderDao.addOrders(newList)
    }

    override suspend fun clearOrders() {
        orderDao.clearOrders()
    }
}

interface OrderRepository : Syncable {
    suspend fun createCashOrder(token: String, cartId: String, request: OrderRequest): Boolean
    fun getOrders(): Flow<List<Order>>
    suspend fun refreshOrders()
    suspend fun clearOrders()
}

fun NetworkOrder.asEntity() = OrderEntity(
    id = id,
    address = shippingAddress.details,
    phone = shippingAddress.phone,
    city = shippingAddress.city,
    taxPrice = taxPrice,
    shippingPrice = shippingPrice,
    totalOrderPrice = totalOrderPrice,
    isDelivered = isDelivered,
    isPaid = isPaid,
    cartItemsText = cartItems.joinToString(";") { "${it.product.id}:${it.count}" },
    paymentMethodType = paymentMethodType,
    createdAt = createdAt,
)

fun OrderEntity.asExternalModel() = Order(
    id = id,
    address = address,
    phone = phone,
    city = city,
    taxPrice = taxPrice,
    shippingPrice = shippingPrice,
    totalOrderPrice = totalOrderPrice,
    isDelivered = isDelivered,
    isPaid = isPaid,
    cartItems = cartItemsText.split(";").associate {
        val (id, count) = it.split(":")
        id to count
    },
    paymentMethodType = paymentMethodType,
    createdAt = createdAt,
)