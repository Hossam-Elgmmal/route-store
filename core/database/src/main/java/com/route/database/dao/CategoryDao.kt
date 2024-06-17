package com.route.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.route.database.model.CategoryEntity

@Dao
interface CategoryDao {

    @Upsert
    suspend fun addCategories(categories: List<CategoryEntity>)

    @Query("select * from categories")
    suspend fun getCategories(): List<CategoryEntity>

    @Query("select * from categories where id = :id")
    suspend fun getCategoryById(id: String): CategoryEntity

    @Query("delete from categories")
    suspend fun clearCategories()

    @Query("delete from categories where id = :id")
    suspend fun deleteCategoryById(id: String)
}