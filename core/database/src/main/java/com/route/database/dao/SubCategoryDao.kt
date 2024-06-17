package com.route.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.route.database.model.SubCategoryEntity

@Dao
interface SubCategoryDao {

    @Upsert
    suspend fun addSubCategories(subcategories: List<SubCategoryEntity>)

    @Query("select * from subcategories")
    suspend fun getSubCategories(): List<SubCategoryEntity>

    @Query("select * from subcategories where categoryId = :categoryId")
    suspend fun getSubCategoriesByCategoryId(categoryId: String): List<SubCategoryEntity>

    @Query("select * from subcategories where id = :id")
    suspend fun getSubCategoryById(id: String): SubCategoryEntity

    @Query("delete from subcategories")
    suspend fun clearSubCategories()

    @Query("delete from subcategories where id = :id")
    suspend fun deleteSubCategoryById(id: String)
}