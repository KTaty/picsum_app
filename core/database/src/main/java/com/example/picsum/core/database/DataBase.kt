package com.example.picsum.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.picsum.core.database.model.ImageEntity
import com.example.picsum.core.database.dao.ImageDao


@Database(entities = [ImageEntity::class],
    version = 1)
internal abstract class Database : RoomDatabase() {
    abstract fun imageDao(): ImageDao

}