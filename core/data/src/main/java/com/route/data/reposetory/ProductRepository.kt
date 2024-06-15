package com.route.data.reposetory

import com.route.data.Syncable
import com.route.data.Synchronizer
import com.route.data.dataVersionSync
import com.route.database.dao.ProductDao
import com.route.database.model.ProductEntity
import com.route.datastore.DataVersion
import com.route.network.model.NetworkRepository
import com.route.network.model.Product
import javax.inject.Inject

private const val TAG = "ProductRepositoryImpl"

class ProductRepositoryImpl @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val productDao: ProductDao,
) : ProductRepository {
    override suspend fun syncWith(synchronizer: Synchronizer): Boolean =
        synchronizer.dataVersionSync(
            versionReader = DataVersion::productVersion,
            fetchDataList = { currentVersion ->
                if (currentVersion <= 0) {
                    networkRepository.getProducts()
                } else {
                    emptyList()
                }
            },
            updateModelData = { productsList ->
                if (productsList.isNotEmpty()) {
                    val newList = productsList.map(Product::asEntity)
                    productDao.addProducts(newList)
                }
            },
            versionUpdater = { latestVersion ->
                copy(productVersion = latestVersion)
            }
        )
}

interface ProductRepository : Syncable

fun Product.asEntity() = ProductEntity(
    id = id,

    subCategoryId = subcategoryList.first().id,
    categoryId = category.id,
    brandId = brand.id,

    title = title,
    description = description,
    imagesUrl = images.joinToString(),
    imageCoverUrl = imageCover,

    searchText = subcategoryList.first().name
            + category.name
            + brand.name
            + title
            + description,

    ratingsQuantity = ratingsQuantity,
    quantity = quantity,
    sold = sold,
    price = price,
    ratingsAverage = ratingsAverage,
)