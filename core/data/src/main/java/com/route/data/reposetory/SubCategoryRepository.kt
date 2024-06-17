package com.route.data.reposetory

import com.route.data.Syncable
import com.route.data.Synchronizer
import com.route.data.dataVersionSync
import com.route.data.model.SubCategory
import com.route.database.dao.SubCategoryDao
import com.route.database.model.SubCategoryEntity
import com.route.datastore.DataVersion
import com.route.network.model.NetworkRepository
import com.route.network.model.NetworkSubCategory
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
                    val newList = subCategoriesLit.map(NetworkSubCategory::asEntity)
                    subCategoryDao.addSubCategories(newList)
                }
            },
            versionUpdater = { latestVersion ->
                copy(subCategoryVersion = latestVersion)
            }
        )

    override suspend fun getSubCategories() =
        subCategoryDao.getSubCategories()
            .map(SubCategoryEntity::asExternalModel)

    override suspend fun getSubCategoriesByCategoryId(categoryId: String) =
        subCategoryDao.getSubCategoriesByCategoryId(categoryId = categoryId)
            .map(SubCategoryEntity::asExternalModel)

    override suspend fun getSubCategoryById(id: String) =
        subCategoryDao.getSubCategoryById(id = id).asExternalModel()

}

interface SubCategoryRepository : Syncable {

    suspend fun getSubCategories(): List<SubCategory>
    suspend fun getSubCategoriesByCategoryId(categoryId: String): List<SubCategory>
    suspend fun getSubCategoryById(id: String): SubCategory
}

fun NetworkSubCategory.asEntity() = SubCategoryEntity(
    id = id,
    name = name,
    categoryId = categoryId
)

fun SubCategoryEntity.asExternalModel() = SubCategory(
    id = id,
    name = name,
    categoryId = categoryId
)