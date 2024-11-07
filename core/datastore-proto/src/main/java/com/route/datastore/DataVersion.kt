package com.route.datastore

data class DataVersion(
    val categoryVersion: Int = 0,
    val subCategoryVersion: Int = 0,
    val brandVersion: Int = 0,
    val productVersion: Int = 0,
    val orderVersion: Int = 0,
)
