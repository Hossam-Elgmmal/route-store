package com.route.data.reposetory

import com.route.data.model.SearchQuery
import com.route.database.dao.SearchQueryDao
import com.route.database.model.SearchQueryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import javax.inject.Inject

class RecentSearchQueryRepositoryImpl @Inject constructor(
    private val searchQueryDao: SearchQueryDao
) : RecentSearchQueryRepository {

    override suspend fun insertSearchQuery(searchQuery: String) {
        searchQueryDao.addSearchQuery(
            SearchQueryEntity(
                query = searchQuery,
                date = Clock.System.now()
            )
        )
    }

    override fun getSearchQuery(limit: Int): Flow<List<SearchQuery>> =
        searchQueryDao.getRecentSearchQueries(limit).map { searchQueries ->
            searchQueries.map(SearchQueryEntity::asExternalModel)
        }


    override suspend fun clearSearchQuery() =
        searchQueryDao.clearSearchQueries()
}

interface RecentSearchQueryRepository {
    suspend fun insertSearchQuery(searchQuery: String)
    fun getSearchQuery(limit: Int): Flow<List<SearchQuery>>
    suspend fun clearSearchQuery()
}

fun SearchQueryEntity.asExternalModel() =
    SearchQuery(
        query = query,
        date = date
    )