package com.example.picsum.core.data


import com.example.picsum.core.model.Image
import kotlinx.coroutines.flow.Flow
import androidx.paging.PagingData

interface ImageRepository {
    fun getRandomPage(): Flow<PagingData<Image>>
    fun getFavoritePage(): Flow<PagingData<Image>>
    fun getFavoriteFlow(): Flow<List<Image>>
    suspend fun updateFavoriteList( list: List<Image>)

}