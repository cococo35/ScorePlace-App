package com.scoreplace.hanple.data.remote

import com.scoreplace.hanple.Weather.WeatherApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherRemoteDataResource {

    @GET("forecast")
    suspend fun getWeather(
        @Query("lat") lat : String?,
        @Query("lon") lon : String?,
        @Query("appid") appid : String?
    ) : WeatherApiResponse
}

