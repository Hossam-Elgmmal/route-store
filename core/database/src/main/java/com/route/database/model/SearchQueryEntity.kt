package com.route.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity("searchQueries")
data class SearchQueryEntity(
    @PrimaryKey
    val query: String,

    val date: Instant
)
