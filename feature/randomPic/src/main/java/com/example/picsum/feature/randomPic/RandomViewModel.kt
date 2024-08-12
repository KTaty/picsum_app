package com.example.picsum.feature.randomPic

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.picsum.core.data.ImageRepository
import com.example.picsum.core.model.Image
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomViewModel @Inject constructor(
    private val imageRepository: ImageRepository,
): ViewModel(){

    private val favoriteImages = imageRepository.getFavoriteFlow()

    fun getImages(): Flow<PagingData<Image>> = imageRepository.getRandomPage().cachedIn(viewModelScope)
                .combine(favoriteImages){ pagingData, favoriteImages ->
                    Log.e("test","newFavorite: $favoriteImages")
                    pagingData.map { image ->
                        image.copy(isFavorite = favoriteImages.any { it.id == image.id })
                    }
                }


    fun toggleFavorite(image: Image) {

        val saveImage = image.copy(isFavorite = !image.isFavorite)
        Log.e("test","$image $saveImage")
        viewModelScope.launch {
            Log.e("test","launch")
            imageRepository.updateFavoriteList(listOf(saveImage))
        }
    }


}