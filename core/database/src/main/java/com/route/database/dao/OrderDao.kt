package com.route.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.route.database.model.OrderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    @Upsert
    suspend fun addOrders(orders: List<OrderEntity>)

    @Query("select * from orders")
    fun getOrders(): Flow<List<OrderEntity>>

    @Query("delete from orders")
    suspend fun clearOrders()

}
