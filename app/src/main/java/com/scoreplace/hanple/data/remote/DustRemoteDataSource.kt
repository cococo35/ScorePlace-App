package com.scoreplace.hanple.data.remote

import com.scoreplace.hanple.Dust.DustApiResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.http.GET
import retrofit2.http.Query




interface DustRemoteDataSource {


    @GET("forecast")
    suspend fun getDustData(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String,
    ): DustApiResponse
}
