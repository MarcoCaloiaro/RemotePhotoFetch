package com.marcocaloiaro.remotephotofetch.model

import com.marcocaloiaro.remotephotofetch.data.local.LocalPhoto

fun Photo.toLocalPhoto(): LocalPhoto {
    return LocalPhoto(
        this.urls.regular,
        this.description,
        this.location.name
    )
}