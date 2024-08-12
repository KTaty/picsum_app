package com.example.picsum.feature.favoritePic

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.picsum.core.data.ImageRepository
import com.example.picsum.core.model.Image
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val imageRepository: ImageRepository,
): ViewModel(){
    private var currentResult: Flow<List<Image>>? = null

    fun getImages(): Flow<List<Image>> {
        val newResult: Flow<List<Image>> =
            imageRepository.getFavoriteFlow()
        currentResult = newResult
        Log.e("test","result:${currentResult}")
        return newResult
    }



}