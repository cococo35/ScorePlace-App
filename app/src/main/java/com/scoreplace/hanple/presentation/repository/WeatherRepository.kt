package com.scoreplace.hanple.data.repository

import com.scoreplace.hanple.Weather.WeatherApiResponse

interface WeatherRepository {
    suspend fun getWeather(
        lat : String?,
        lon : String?,
        appid : String?
    ) : WeatherApiResponse
}