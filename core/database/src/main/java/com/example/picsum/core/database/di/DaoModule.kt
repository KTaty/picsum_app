package com.example.picsum.core.database.di

import com.example.picsum.core.database.Database
import com.example.picsum.core.database.dao.ImageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {
    @Provides
    fun providesImageDao(
        database: Database,
    ): ImageDao = database.imageDao()

}
