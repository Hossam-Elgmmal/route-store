package com.route.data.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class SearchQuery(
    val query: String,
    val date: Instant = Clock.System.now(),
)
