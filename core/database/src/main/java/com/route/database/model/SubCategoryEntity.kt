package com.route.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subcategories")
data class SubCategoryEntity(

    @PrimaryKey
    val id: String,

    val name: String,

    val categoryId: String,

    )
