package com.route.data.reposetory

import android.util.Log
import com.route.data.Syncable
import com.route.data.Synchronizer
import com.route.data.dataVersionSync
import com.route.datastore.DataVersion
import com.route.network.model.Product
import javax.inject.Inject

private const val TAG = "ProductRepositoryImpl"

class ProductRepositoryImpl @Inject constructor() : ProductRepository {
    override suspend fun syncWith(synchronizer: Synchronizer): Boolean =
        synchronizer.dataVersionSync(
            versionReader = DataVersion::productVersion,
            fetchDataList = { currentVersion ->
                if (currentVersion >= 0) {
                    Log.i(TAG, "syncWith: $currentVersion")
                    // network.get
                    emptyList<Product>()
                } else {
                    emptyList()
                }
            },
            updateModelData = {
                // TODO("need database")
            },
            versionUpdater = { latestVersion ->
                copy(productVersion = latestVersion)
            }
        )
}

interface ProductRepository : Syncable