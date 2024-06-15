package com.route.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Categories")
data class CategoryEntity(

    @PrimaryKey
    val id: String,

    val name: String,

    val imageUrl: String,

    )
