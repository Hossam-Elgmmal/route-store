package com.route.data.reposetory

import com.route.data.Syncable
import com.route.data.Synchronizer

class OrderRepositoryImpl : OrderRepository {
    override suspend fun syncWith(synchronizer: Synchronizer): Boolean {
        TODO("Not yet implemented")
    }
}

interface OrderRepository : Syncable