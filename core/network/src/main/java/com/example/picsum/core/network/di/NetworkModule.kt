package com.example.picsum.core.network.di

import com.example.magentatest.core.network.BASE_URL
import com.example.picsum.core.network.ImageNetworkDataSource
import com.example.picsum.core.network.ImageNetworkRetrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        //encodeDefaults = true
    }

    @Provides
    @Singleton
    fun providesRetrofit(
        providesNetworkJson:Json
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(providesNetworkJson.asConverterFactory("application/json".toMediaType()))
        .build()


    @Provides
    @Singleton
    fun providesNetworkDataSource(
        retrofit: Retrofit
    ): ImageNetworkDataSource {
        return ImageNetworkRetrofit(retrofit)
    }


}