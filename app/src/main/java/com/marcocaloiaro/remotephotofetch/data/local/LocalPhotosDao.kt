package com.marcocaloiaro.remotephotofetch.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marcocaloiaro.remotephotofetch.data.local.LocalPhoto
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalPhotosDao {
    @Query("SELECT * FROM localphoto")
    fun getAllPhotos(): Flow<List<LocalPhoto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPhoto(localPhoto: LocalPhoto)
}