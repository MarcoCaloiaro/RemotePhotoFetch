package com.marcocaloiaro.remotephotofetch.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalPhoto(
    @PrimaryKey val url: String,
    val description: String?,
    val location: String?
)