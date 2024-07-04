package com.scoreplace.hanple.Weather

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

