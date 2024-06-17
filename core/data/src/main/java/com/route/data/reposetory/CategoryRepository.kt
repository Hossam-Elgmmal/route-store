package com.route.data.reposetory

import com.route.data.Syncable
import com.route.data.Synchronizer
import com.route.data.dataVersionSync
import com.route.data.model.Category
import com.route.database.dao.CategoryDao
import com.route.database.model.CategoryEntity
import com.route.datastore.DataVersion
import com.route.network.model.NetworkCategory
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
                    val newList = categoriesList.map(NetworkCategory::asEntity)
                    categoryDao.addCategories(newList)
                }
            },
            versionUpdater = { latestVersion ->
                copy(categoryVersion = latestVersion)
            }
        )

    override suspend fun getCategories() =
        categoryDao.getCategories().map(CategoryEntity::asExternalModel)

    override suspend fun getCategoryById(id: String) =
        categoryDao.getCategoryById(id = id).asExternalModel()

}

interface CategoryRepository : Syncable {

    suspend fun getCategories(): List<Category>
    suspend fun getCategoryById(id: String): Category
}

fun NetworkCategory.asEntity() = CategoryEntity(
    id = id,
    name = name,
    imageUrl = imageUrl
)

fun CategoryEntity.asExternalModel() = Category(
    id = id,
    name = name,
    imageUrl = imageUrl
)