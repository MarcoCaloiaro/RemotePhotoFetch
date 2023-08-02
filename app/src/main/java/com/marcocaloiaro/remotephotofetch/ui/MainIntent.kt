package com.marcocaloiaro.remotephotofetch.ui

import com.marcocaloiaro.remotephotofetch.data.local.LocalPhoto

sealed class MainIntent {
    data class PhotoSelected(val photo: LocalPhoto): MainIntent()
    object ScreenLaunched : MainIntent()
    object AddPhotoClicked : MainIntent()
    object CloseSheetClicked : MainIntent()
}