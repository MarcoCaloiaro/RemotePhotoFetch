package com.marcocaloiaro.remotephotofetch.model

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("urls") var urls: Urls,
    @SerializedName("location") var location: Location,
    @SerializedName("description") var description: String
)


data class Urls(
    @SerializedName("regular") var regular: String
)

data class Location(
    @SerializedName("name") var name: String
)
