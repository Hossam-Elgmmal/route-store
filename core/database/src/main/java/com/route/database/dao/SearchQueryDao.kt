package com.route.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.route.database.model.SearchQueryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchQueryDao {

    @Query("select * from searchQueries order by date desc limit :limit")
    fun getRecentSearchQueries(limit: Int): Flow<List<SearchQueryEntity>>

    @Upsert
    suspend fun addSearchQuery(searchQuery: SearchQueryEntity)

    @Query("delete from searchQueries")
    suspend fun clearSearchQueries()
}