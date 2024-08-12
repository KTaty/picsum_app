package com.example.picsum.core.network

import com.example.picsum.core.network.model.ImageNetwork
import retrofit2.Retrofit

internal class ImageNetworkRetrofit (
    retrofit: Retrofit
): ImageNetworkDataSource {
    private val apiService : PicsumApi = retrofit.create(PicsumApi::class.java)

    override suspend fun getImageList(page: Int, limit: Int): Result<List<ImageNetwork>> {
        return try {
            val response = apiService.getImages(page, limit)
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Network error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}