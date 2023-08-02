package com.marcocaloiaro.remotephotofetch.data.remote

import com.marcocaloiaro.remotephotofetch.model.Photo
import retrofit2.http.GET

interface PhotoApiService {
    // API Key is here for simplicity, but it should be stored secretly.
    @GET("photos/random/?client_id=wrvjZRhE0K_xN2jjD2LqFKaYHx8kxwAhZY27euw1EtU")
    suspend fun getRandomPhoto(): Photo
}
