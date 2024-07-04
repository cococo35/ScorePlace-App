package com.scoreplace.hanple.domain.remote

import com.google.gson.annotations.SerializedName

data class WeatherApiResponseEntity (
    val cod: String?,
    val message : String?,
    val cnt : String,
    val list: List<WeatherResponseEntity>?,
)

data class WeatherResponseEntity(
    val dt: String?,
    val main: WeatherMainEntity?,
    val weather: List<WeatherEntity>,
    val cloud: CloudEntity?,
    val wind: WindEntity?,
    val visibility: String?,
    val pop: String?,
    val sys: SysEntity?,
    val dt_txt: String?,
)

data class WeatherMainEntity(
    val temp: String?,
    val feels_like: String?,
    val temp_min: String?,
    val temp_max: String?,
    val pressure: String?,
    val sea_level: String?,
    val grnd_level: String?,
    val humidity: String?,
    val temp_kf: String?
)
data class CloudEntity(
    val all: String?,
)
data class WindEntity(
    val speed: String?,
    val deg: String?,
    val gust: String?,
)
data class SysEntity(
    val pod: String?
)
data class WeatherEntity(
    val id: String?,
    val main: String?,
    val description: String?,
    val icon: String?,
)