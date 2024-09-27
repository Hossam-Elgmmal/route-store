package com.route.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.route.database.model.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Upsert
    suspend fun addProducts(products: List<ProductEntity>)

    @Query("select * from products")
    fun getProducts(): Flow<List<ProductEntity>>

    @Query("select * from products where id = :id")
    suspend fun getProductById(id: String): ProductEntity

    @Query("select * from products where categoryId = :categoryId")
    suspend fun getProductsByCategoryId(categoryId: String): List<ProductEntity>

    @Query("select * from products where subCategoryId = :subCategoryId")
    suspend fun getProductsBySubCategoryId(subCategoryId: String): List<ProductEntity>

    @Query("select * from products where brandId = :brandId")
    suspend fun getProductsByBrandId(brandId: String): List<ProductEntity>

    @Query("select * from products where searchText like '%' || :query || '%'")
    suspend fun searchProducts(query: String): List<ProductEntity>

    @Query("delete from products")
    suspend fun clearProducts()

    @Query("delete from products where id = :id")
    suspend fun deleteProductById(id: String)

    @Query("select * from products where id in (:idList)")
    fun getProductsInCart(idList: List<String>): Flow<List<ProductEntity>>
}