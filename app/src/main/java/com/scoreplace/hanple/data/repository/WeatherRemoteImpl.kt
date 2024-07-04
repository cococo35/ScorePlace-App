package com.scoreplace.hanple.data.repository

import com.scoreplace.hanple.Weather.WeatherRemoteDataResource

class WeatherRemoteImpl(
    private val weatherRemoteDataResource: WeatherRemoteDataResource
) : WeatherRepository {
    override suspend fun getWeather(
        lat: String?,
        lon: String?,
        appid: String?
    ) = weatherRemoteDataResource.getWeather(lat, lon, appid)

}