package com.route.data.reposetory

import com.route.data.Syncable
import com.route.data.Synchronizer
import com.route.data.dataVersionSync
import com.route.data.model.Product
import com.route.database.dao.ProductDao
import com.route.database.model.ProductEntity
import com.route.datastore.DataVersion
import com.route.network.model.NetworkProduct
import com.route.network.model.NetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
                    val newList = productsList.map(NetworkProduct::asEntity)
                    productDao.addProducts(newList)
                }
            },
            versionUpdater = { latestVersion ->
                copy(productVersion = latestVersion)
            }
        )

    override fun getProducts() =
        productDao.getProducts()
            .map { it.map(ProductEntity::asExternalModel) }

    override fun getProductById(id: String) =
        productDao.getProductById(id = id).map(ProductEntity::asExternalModel)

    override suspend fun getProductsByCategoryId(categoryId: String) =
        productDao.getProductsByCategoryId(categoryId = categoryId)
            .map(ProductEntity::asExternalModel)

    override suspend fun getProductsBySubCategoryId(subCategoryId: String) =
        productDao.getProductsBySubCategoryId(subCategoryId = subCategoryId)
            .map(ProductEntity::asExternalModel)

    override suspend fun getProductsByBrandId(brandId: String) =
        productDao.getProductsByBrandId(brandId = brandId)
            .map(ProductEntity::asExternalModel)

    override suspend fun searchProducts(query: String) =
        productDao.searchProducts(query = query)
            .map(ProductEntity::asExternalModel)

    override fun getProductsInCart(idList: List<String>) =
        productDao.getProductsInCart(idList)
            .map { it.map(ProductEntity::asExternalModel) }
}

interface ProductRepository : Syncable {

    fun getProducts(): Flow<List<Product>>
    fun getProductById(id: String): Flow<Product>

    suspend fun getProductsByCategoryId(categoryId: String): List<Product>
    suspend fun getProductsBySubCategoryId(subCategoryId: String): List<Product>
    suspend fun getProductsByBrandId(brandId: String): List<Product>

    suspend fun searchProducts(query: String): List<Product>
    fun getProductsInCart(idList: List<String>): Flow<List<Product>>

}

fun NetworkProduct.asEntity() = ProductEntity(
    id = id,

    subCategoryId = subcategoryList.first().id,
    categoryId = networkCategory.id,
    brandId = networkBrand.id,

    title = title,
    description = description,
    imagesUrl = images.joinToString(),
    imageCoverUrl = imageCover,

    searchText = subcategoryList.first().name
            + networkCategory.name
            + networkBrand.name
            + title,

    ratingsQuantity = quantity,
    quantity = ratingsQuantity,
    sold = sold,
    price = price,
    ratingsAverage = ratingsAverage,
)

fun ProductEntity.asExternalModel() = Product(
    id = id,

    subCategoryId = subCategoryId,
    categoryId = categoryId,
    brandId = brandId,

    title = title,
    description = description,
    imagesUrlList = imagesUrl.split(", "),
    imageCoverUrl = imageCoverUrl,

    ratingsQuantity = ratingsQuantity,
    quantity = quantity,
    sold = sold,
    price = price,
    ratingsAverage = ratingsAverage,
)