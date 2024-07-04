package com.scoreplace.hanple.data.repository

import com.scoreplace.hanple.data.remote.WeatherRemoteDataResource
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherRemoteDataResource: WeatherRemoteDataResource
) : WeatherRepository {
    override suspend fun getWeather(
        lat: String?,
        lon: String?,
        appid: String?
    ) = weatherRemoteDataResource.getWeather(lat, lon, appid)

}