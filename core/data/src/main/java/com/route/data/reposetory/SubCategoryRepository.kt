package com.route.data.reposetory

import com.route.data.Syncable
import com.route.data.Synchronizer
import com.route.data.dataVersionSync
import com.route.database.dao.SubCategoryDao
import com.route.database.model.SubCategoryEntity
import com.route.datastore.DataVersion
import com.route.network.model.NetworkRepository
import com.route.network.model.SubCategory
import javax.inject.Inject

private const val TAG = "SubCategoryRepositoryImpl"

class SubCategoryRepositoryImpl @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val subCategoryDao: SubCategoryDao,
) : SubCategoryRepository {
    override suspend fun syncWith(synchronizer: Synchronizer): Boolean =
        synchronizer.dataVersionSync(
            versionReader = DataVersion::subCategoryVersion,
            fetchDataList = { currentVersion ->
                if (currentVersion <= 0) {
                    networkRepository.getSubCategories()
                } else {
                    emptyList()
                }
            },
            updateModelData = { subCategoriesLit ->
                if (subCategoriesLit.isNotEmpty()) {
                    val newList = subCategoriesLit.map(SubCategory::asEntity)
                    subCategoryDao.addSubCategories(newList)
                }
            },
            versionUpdater = { latestVersion ->
                copy(subCategoryVersion = latestVersion)
            }
        )
}

interface SubCategoryRepository : Syncable

fun SubCategory.asEntity() = SubCategoryEntity(
    id = id,
    name = name,
    categoryId = categoryId
)