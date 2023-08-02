package com.marcocaloiaro.remotephotofetch.ui

import com.marcocaloiaro.remotephotofetch.data.local.LocalPhoto

data class MainViewState(
    val photos: List<LocalPhoto>,
    val selectedPhoto: LocalPhoto?,
    val shouldLoad: Boolean
) {
    companion object {
        val empty = MainViewState(
            photos = emptyList(),
            selectedPhoto = null,
            shouldLoad = true
        )
    }
}