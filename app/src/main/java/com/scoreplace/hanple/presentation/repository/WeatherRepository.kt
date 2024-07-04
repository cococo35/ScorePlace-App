package com.scoreplace.hanple.data.repository

import com.scoreplace.hanple.Weather.WeatherApiResponse
import com.scoreplace.hanple.domain.remote.WeatherApiResponseEntity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent


interface WeatherRepository {

    suspend fun getWeather(
        lat : String?,
        lon : String?,
        appid : String?
    ) : WeatherApiResponseEntity
}