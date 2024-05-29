package com.android.hanple.Weather

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherRemote {

    @GET("forecast")
    suspend fun getWeather(
        @Query("lat") lat : String?,
        @Query("lon") lon : String?,
        @Query("appid") appid : String?
    ) : WeatherApiResponse
}


class WeatherRemoteImpl(
    private val weatherRemote: WeatherRemote
) : WeatherRemote {
    override suspend fun getWeather(
        lat: String?,
        lon: String?,
        appid: String?
    ) = weatherRemote.getWeather(lat, lon, appid)

}