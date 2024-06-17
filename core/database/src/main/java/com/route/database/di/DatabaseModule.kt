package com.route.database.di

import android.content.Context
import androidx.room.Room
import com.route.database.EcomDatabase
import com.route.database.dao.BrandDao
import com.route.database.dao.CategoryDao
import com.route.database.dao.ProductDao
import com.route.database.dao.SubCategoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideEcomDatabase(
        @ApplicationContext context: Context
    ): EcomDatabase =
        Room.databaseBuilder(
            context,
            EcomDatabase::class.java,
            "ecom_database"
        ).build()


    @Provides
    fun provideCategoryDao(
        database: EcomDatabase
    ): CategoryDao = database.getCategoryDao()

    @Provides
    fun provideSubCategoryDao(
        database: EcomDatabase
    ): SubCategoryDao = database.getSubCategoryDao()

    @Provides
    fun provideBrandDao(
        database: EcomDatabase
    ): BrandDao = database.getBrandDao()

    @Provides
    fun provideProductDao(
        database: EcomDatabase
    ): ProductDao = database.getProductDao()

}