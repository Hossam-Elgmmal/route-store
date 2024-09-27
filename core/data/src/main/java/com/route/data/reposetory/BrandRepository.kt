package com.route.data.reposetory

import com.route.data.Syncable
import com.route.data.Synchronizer
import com.route.data.dataVersionSync
import com.route.data.model.Brand
import com.route.database.dao.BrandDao
import com.route.database.model.BrandEntity
import com.route.datastore.DataVersion
import com.route.network.model.NetworkBrand
import com.route.network.model.NetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val TAG = "BrandRepositoryImpl"

class BrandRepositoryImpl @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val brandDao: BrandDao,
) : BrandRepository {

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean =
        synchronizer.dataVersionSync(
            versionReader = DataVersion::brandVersion,
            fetchDataList = { currentVersion ->
                if (currentVersion <= 0) {
                    networkRepository.getBrands()
                } else {
                    emptyList()
                }
            },
            updateModelData = { brandList ->
                if (brandList.isNotEmpty()) {
                    val newList = brandList.map(NetworkBrand::asEntity)
                    brandDao.addBrands(newList)
                }
            },
            versionUpdater = { latestVersion ->
                copy(brandVersion = latestVersion)
            }
        )

    override fun getBrands() =
        brandDao.getBrands().map { it.map(BrandEntity::asExternalModel) }

    override suspend fun getBrandById(id: String) =
        brandDao.getBrandById(id = id).asExternalModel()
}

interface BrandRepository : Syncable {

    fun getBrands(): Flow<List<Brand>>
    suspend fun getBrandById(id: String): Brand
}

fun NetworkBrand.asEntity() = BrandEntity(
    id = id,
    name = name,
    imageUrl = imageUrl
)

fun BrandEntity.asExternalModel() = Brand(
    id = id,
    name = name,
    imageUrl = imageUrl
)