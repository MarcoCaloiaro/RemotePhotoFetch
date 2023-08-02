package com.marcocaloiaro.remotephotofetch

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import jonathanfinerty.once.Once

@HiltAndroidApp
class RemotePhotoFetchApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Once.initialise(this)
    }
}