package com.route.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.route.database.model.CartProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartProductDao {

    @Upsert
    suspend fun updateCartProducts(products: List<CartProductEntity>)

    @Upsert
    suspend fun addCartProduct(product: CartProductEntity)

    @Query("select count from cartProducts where id = :productId")
    suspend fun getProductCount(productId: String): Int

    @Query("select * from cartProducts")
    fun getCartProducts(): Flow<List<CartProductEntity>>

    @Query("delete from cartProducts where id = :productId")
    suspend fun removeCartProduct(productId: String)
}