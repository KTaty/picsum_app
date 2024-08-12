package com.example.picsum.core.data.di


import com.example.picsum.core.data.ImageRepository
import com.example.picsum.core.data.ImageRepositoryImpl
import com.example.picsum.core.database.dao.ImageDao
import com.example.picsum.core.network.ImageNetworkDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {


    @Provides
    @Singleton
    fun provideImageRepository(
        networkDataSource: ImageNetworkDataSource,
        localDataBase: ImageDao
    ): ImageRepository {
        return ImageRepositoryImpl(
            localDataBase = localDataBase,
            networkDataSource = networkDataSource
        )
    }

}