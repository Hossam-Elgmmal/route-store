package com.route.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.route.database.dao.BrandDao
import com.route.database.dao.CategoryDao
import com.route.database.dao.ProductDao
import com.route.database.dao.SearchQueryDao
import com.route.database.dao.SubCategoryDao
import com.route.database.model.BrandEntity
import com.route.database.model.CategoryEntity
import com.route.database.model.ProductEntity
import com.route.database.model.SearchQueryEntity
import com.route.database.model.SubCategoryEntity
import com.route.database.utils.InstantConverter

@Database(
    entities = [
        BrandEntity::class,
        CategoryEntity::class,
        ProductEntity::class,
        SubCategoryEntity::class,
        SearchQueryEntity::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(InstantConverter::class)
abstract class EcomDatabase : RoomDatabase() {
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getSubCategoryDao(): SubCategoryDao
    abstract fun getBrandDao(): BrandDao
    abstract fun getProductDao(): ProductDao
    abstract fun getSearchQueryDao(): SearchQueryDao
}