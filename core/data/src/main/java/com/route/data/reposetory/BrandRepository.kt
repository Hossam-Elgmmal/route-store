package com.route.data.reposetory

import com.route.data.Syncable
import com.route.data.Synchronizer
import com.route.data.dataVersionSync
import com.route.database.dao.BrandDao
import com.route.database.model.BrandEntity
import com.route.datastore.DataVersion
import com.route.network.model.Brand
import com.route.network.model.NetworkRepository
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
                    val newList = brandList.map(Brand::asEntity)
                    brandDao.addBrands(newList)
                }
            },
            versionUpdater = { latestVersion ->
                copy(brandVersion = latestVersion)
            }
        )
}

interface BrandRepository : Syncable

fun Brand.asEntity() = BrandEntity(
    id = id,
    name = name,
    imageUrl = imageUrl
)