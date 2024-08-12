package com.example.picsum.core.network

import com.example.picsum.core.network.model.ImageNetwork
import retrofit2.Response
import retrofit2.http.Query
import retrofit2.http.GET

internal interface PicsumApi {
    @GET("v2/list")
    suspend fun getImages(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 100,
    ): Response<List<ImageNetwork>>
}