package com.route.data.reposetory

import android.util.Log
import com.route.data.Syncable
import com.route.data.Synchronizer
import com.route.data.dataVersionSync
import com.route.datastore.DataVersion
import com.route.network.model.Category
import javax.inject.Inject

private const val TAG = "CategoryRepositoryImpl"

class CategoryRepositoryImpl @Inject constructor() : CategoryRepository {
    override suspend fun syncWith(synchronizer: Synchronizer): Boolean =
        synchronizer.dataVersionSync(
            versionReader = DataVersion::categoryVersion,
            fetchDataList = { currentVersion ->
                if (currentVersion >= 0) {
                    Log.i(TAG, "syncWith: $currentVersion")
                    // network.get
                    emptyList<Category>()
                } else {
                    emptyList()
                }
            },
            updateModelData = {
                // TODO("need database")
            },
            versionUpdater = { latestVersion ->
                copy(categoryVersion = latestVersion)
            }
        )
}

interface CategoryRepository : Syncable