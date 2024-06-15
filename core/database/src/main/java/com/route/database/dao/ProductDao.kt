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
    fun getProductById(id: String): Flow<ProductEntity>

    @Query("select * from products where categoryId = :categoryId")
    fun getProductsByCategoryId(categoryId: String): Flow<List<ProductEntity>>

    @Query("select * from products where subCategoryId = :subCategoryId")
    fun getProductsBySubCategoryId(subCategoryId: String): Flow<List<ProductEntity>>

    @Query("select * from products where brandId = :brandId")
    fun getProductsByBrandId(brandId: String): Flow<List<ProductEntity>>

    @Query("select * from products where searchText like '%' || :query || '%'")
    fun searchProducts(query: String): Flow<List<ProductEntity>>

    @Query("delete from products")
    suspend fun clearProducts()

    @Query("delete from products where id = :id")
    suspend fun deleteProductById(id: String)
}