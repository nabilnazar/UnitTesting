package com.meghamlabs.unittesting.data.remote

import androidx.room.PrimaryKey
import com.meghamlabs.unittesting.BuildConfig
import com.meghamlabs.unittesting.data.remote.responses.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {

    @GET("/api/")
    suspend fun searchForImage(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = BuildConfig.API_KEY
    ):Response<ImageResponse>



}