package com.example.picsum.core.network

import com.example.picsum.core.network.model.ImageNetwork

interface ImageNetworkDataSource {
    suspend fun getImageList(page: Int, limit: Int): Result<List<ImageNetwork>>
}