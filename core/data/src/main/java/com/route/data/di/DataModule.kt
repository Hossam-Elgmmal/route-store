package com.route.data.di

import com.route.data.ConnectivityNetworkMonitor
import com.route.data.NetworkMonitor
import com.route.data.reposetory.BrandRepository
import com.route.data.reposetory.BrandRepositoryImpl
import com.route.data.reposetory.CategoryRepository
import com.route.data.reposetory.CategoryRepositoryImpl
import com.route.data.reposetory.ProductRepository
import com.route.data.reposetory.ProductRepositoryImpl
import com.route.data.reposetory.SubCategoryRepository
import com.route.data.reposetory.SubCategoryRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindsCategoryRepository(
        categoryRepository: CategoryRepositoryImpl
    ): CategoryRepository

    @Binds
    abstract fun bindsBrandRepository(
        brandRepository: BrandRepositoryImpl
    ): BrandRepository

    @Binds
    abstract fun bindsProductRepository(
        productRepository: ProductRepositoryImpl
    ): ProductRepository

    @Binds
    abstract fun bindsSubCategoryRepository(
        subCategoryRepository: SubCategoryRepositoryImpl
    ): SubCategoryRepository

    @Binds
    abstract fun bindNetworkMonitor(
        networkMonitor: ConnectivityNetworkMonitor
    ): NetworkMonitor

}