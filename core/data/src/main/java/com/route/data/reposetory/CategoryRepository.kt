package com.route.data.reposetory

import com.route.data.Syncable
import com.route.data.Synchronizer
import com.route.data.dataVersionSync
import com.route.database.dao.CategoryDao
import com.route.database.model.CategoryEntity
import com.route.datastore.DataVersion
import com.route.network.model.Category
import com.route.network.model.NetworkRepository
import javax.inject.Inject

private const val TAG = "CategoryRepositoryImpl"

class CategoryRepositoryImpl @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val categoryDao: CategoryDao
) : CategoryRepository {
    override suspend fun syncWith(synchronizer: Synchronizer): Boolean =
        synchronizer.dataVersionSync(
            versionReader = DataVersion::categoryVersion,
            fetchDataList = { currentVersion ->
                if (currentVersion <= 0) {
                    networkRepository.getCategories()
                } else {
                    emptyList()
                }
            },
            updateModelData = { categoriesList ->
                if (categoriesList.isNotEmpty()) {
                    val newList = categoriesList.map(Category::asEntity)
                    categoryDao.addCategories(newList)
                }
            },
            versionUpdater = { latestVersion ->
                copy(categoryVersion = latestVersion)
            }
        )
}

interface CategoryRepository : Syncable

fun Category.asEntity() = CategoryEntity(
    id = id,
    name = name,
    imageUrl = imageUrl
)