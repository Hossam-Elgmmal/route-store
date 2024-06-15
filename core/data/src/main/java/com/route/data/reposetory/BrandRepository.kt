package com.route.data.reposetory

import android.util.Log
import com.route.data.Syncable
import com.route.data.Synchronizer
import com.route.data.dataVersionSync
import com.route.datastore.DataVersion
import com.route.network.model.Brand
import javax.inject.Inject

private const val TAG = "BrandRepositoryImpl"

class BrandRepositoryImpl @Inject constructor() : BrandRepository {
    override suspend fun syncWith(synchronizer: Synchronizer): Boolean =
        synchronizer.dataVersionSync(
            versionReader = DataVersion::brandVersion,
            fetchDataList = { currentVersion ->
                if (currentVersion >= 0) {
                    Log.i(TAG, "syncWith: $currentVersion")
                    // network.get
                    emptyList<Brand>()
                } else {
                    emptyList()
                }
            },
            updateModelData = {
                // TODO("need database")
            },
            versionUpdater = { latestVersion ->
                copy(brandVersion = latestVersion)
            }
        )
}

interface BrandRepository : Syncable