package com.example.picsum.core.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.picsum.core.database.dao.ImageDao
import com.example.picsum.core.database.model.ImageEntity
import com.example.picsum.core.model.Image
import com.example.picsum.core.network.ImageNetworkDataSource
import com.example.picsum.core.network.model.ImageNetwork
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ImageRepositoryImpl(
    private val localDataBase: ImageDao,
    private val networkDataSource: ImageNetworkDataSource

): ImageRepository {


    override fun getRandomPage(): Flow<PagingData<Image>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 30),
            pagingSourceFactory = {
                PagingDataSource<ImageNetwork>(
                        { page, loadSize ->
                            networkDataSource.getImageList(page, loadSize).getOrThrow()
                        }){ item ->
                        Image(id = item.id, url = item.download_url,isFavorite = false)
                }
            }
        ).flow
    }

    override fun getFavoritePage(): Flow<PagingData<Image>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 30),
            pagingSourceFactory = {
                PagingDataSource<ImageEntity>(localDataBase::getFavoritePage){ item ->
                    Image(id = item.id, url = item.url,isFavorite = true)
                }
            }
        ).flow
    }

    override fun getFavoriteFlow(): Flow<List<Image>> {
        return localDataBase.getFavoriteImgFlow().map { imageEntities ->
            imageEntities.map { imageEntity ->
                Image(id = imageEntity.id, url = imageEntity.url, isFavorite = true)
            }
        }
    }

    override suspend fun updateFavoriteList(list: List<Image>) {
        val (favoriteImages, nonFavoriteImages) = list.partition { it.isFavorite }

        if (favoriteImages.isNotEmpty()) {
            val favoriteEntities = favoriteImages.map { ImageEntity(it.id, it.url) }
            localDataBase.insertOrIgnoreOffers(favoriteEntities)
        }

        if (nonFavoriteImages.isNotEmpty()) {
            val nonFavoriteEntities = nonFavoriteImages.map { ImageEntity(it.id, it.url) }
            localDataBase.delImage(nonFavoriteEntities)
        }
    }

}