package com.example.picsum.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorites"
)
data class ImageEntity(
    @PrimaryKey
    val id: Int,
    val url: String
)