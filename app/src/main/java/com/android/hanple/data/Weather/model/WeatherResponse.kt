package com.android.hanple.Weather

import com.google.gson.annotations.SerializedName

data class WeatherApiResponse (
    @SerializedName("cod") val cod: String?,
    @SerializedName("message") val message : String?,
    @SerializedName("cnt") val cnt : String,
    @SerializedName("list") val list: List<WeatherResponse>?,
)

data class WeatherResponse(
    @SerializedName("dt") val dt: String?,
    @SerializedName("main") val main: WeatherMain?,
    @SerializedName("weather") val weather: List<Weather>,
    @SerializedName("cloud") val cloud: Cloud?,
    @SerializedName("wind") val wind: Wind?,
    @SerializedName("visibility") val visibility: String?,
    @SerializedName("pop") val pop: String?,
    @SerializedName("sys") val sys: Sys?,
    @SerializedName("dt_txt") val dt_txt: String?,

)
data class WeatherMain(
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
data class Cloud(
    val all: String?,
)
data class Wind(
    val speed: String?,
    val deg: String?,
    val gust: String?,
)
data class Sys(
    val pod: String?
)
data class Weather(
    val id: String?,
    val main: String?,
    val description: String?,
    val icon: String?,
)