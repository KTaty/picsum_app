package com.example.picsum.core.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.picsum.core.model.Image
import java.io.IOException

internal class PagingDataSource<T> (
    private val loadPage: suspend (Int,Int)-> List <T>,
    private val mapper: (T) -> Image
) : PagingSource<Int, Image>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        val page = params.key ?: 1
        val loadSize =  params.loadSize
        return try {
            val data = loadPage(page,loadSize)
            LoadResult.Page(
                data = data.map{
                    mapper(it)
                },
                prevKey = if (page<=1) null else page - 1 ,
                nextKey = if (data.size < loadSize) null else page + 1
            )
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    LoadResult.Error(IOException("Please check internet connection"))
                }
                else -> {
                    LoadResult.Error(t)
                }
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        return state.anchorPosition
    }

}