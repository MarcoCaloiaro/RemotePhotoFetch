package com.marcocaloiaro.remotephotofetch.di

import android.content.Context
import androidx.room.Room
import com.marcocaloiaro.remotephotofetch.data.local.LocalPhotoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, LocalPhotoDatabase::class.java, "local_photo_database")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao(db: LocalPhotoDatabase) = db.localPhotosDao()
}