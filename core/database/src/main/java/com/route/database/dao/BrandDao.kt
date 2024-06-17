package com.route.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.route.database.model.BrandEntity

@Dao
interface BrandDao {

    @Upsert
    suspend fun addBrands(brands: List<BrandEntity>)

    @Query("select * from brands")
    suspend fun getBrands(): List<BrandEntity>

    @Query("select * from brands where id = :id")
    suspend fun getBrandById(id: String): BrandEntity

    @Query("delete from brands")
    suspend fun clearBrands()

    @Query("delete from brands where id = :id")
    suspend fun deleteBrandById(id: String)

}