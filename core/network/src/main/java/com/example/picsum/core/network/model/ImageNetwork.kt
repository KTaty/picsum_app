package com.example.picsum.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ImageNetwork(
    val id: Int,
    val author: String?,
    val width: Int,
    val height: Int,
    val url: String,
    val download_url: String,
)

