package com.route.data.reposetory

import android.util.Log
import com.route.data.Syncable
import com.route.data.Synchronizer
import com.route.data.dataVersionSync
import com.route.datastore.DataVersion
import com.route.network.model.SubCategory
import javax.inject.Inject

private const val TAG = "SubCategoryRepositoryImpl"

class SubCategoryRepositoryImpl @Inject constructor() : SubCategoryRepository {
    override suspend fun syncWith(synchronizer: Synchronizer): Boolean =
        synchronizer.dataVersionSync(
            versionReader = DataVersion::subCategoryVersion,
            fetchDataList = { currentVersion ->
                if (currentVersion >= 0) {
                    Log.i(TAG, "syncWith: $currentVersion")
                    // network.get
                    emptyList<SubCategory>()
                } else {
                    emptyList()
                }
            },
            updateModelData = {
                // TODO("need database")
            },
            versionUpdater = { latestVersion ->
                copy(subCategoryVersion = latestVersion)
            }
        )
}

interface SubCategoryRepository : Syncable