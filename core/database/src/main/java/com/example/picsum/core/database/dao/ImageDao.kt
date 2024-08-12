package com.example.picsum.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.picsum.core.database.model.ImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreOffers(imageEntities: List<ImageEntity>)

    @Delete
    suspend fun delImage(imageEntities: List<ImageEntity>)

    @Query("DELETE FROM favorites WHERE id IN (:id)")
    suspend fun deleteImagesByUris(id: List<Int>)

    @Query("SELECT * FROM favorites")
    fun getFavoriteImgFlow(): Flow<List<ImageEntity>>

    @Query("SELECT * FROM favorites")
    fun getFavoriteImage(): List<ImageEntity>

    @Query("SELECT * FROM favorites LIMIT :pageSize OFFSET (:page -1 * :pageSize)")
    suspend fun getFavoritePage(page: Int, pageSize: Int): List<ImageEntity>

}