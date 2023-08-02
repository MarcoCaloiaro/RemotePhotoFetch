package com.marcocaloiaro.remotephotofetch.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalPhoto::class], version = 1)
abstract class LocalPhotoDatabase : RoomDatabase() {
    abstract fun localPhotosDao(): LocalPhotosDao
}