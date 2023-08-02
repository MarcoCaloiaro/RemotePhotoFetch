package com.marcocaloiaro.remotephotofetch.data.local

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalPhotoRepository @Inject constructor(
    private val localPhotosDao: LocalPhotosDao
) {
    fun getAllPhotos(): Flow<List<LocalPhoto>> = localPhotosDao.getAllPhotos()

    fun insertPhoto(photo: LocalPhoto) = localPhotosDao.addPhoto(photo)
}