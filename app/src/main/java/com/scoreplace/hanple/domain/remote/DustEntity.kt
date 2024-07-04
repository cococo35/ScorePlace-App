package com.scoreplace.hanple.domain.remote

import com.google.gson.annotations.SerializedName

data class DustApiResponseEntity(
    val coord: CoordEntity?,
    val list: List<DustResponseEntity>?
)

data class DustResponseEntity(
    val main: DustMainEntity?,
    val components: DustComponentsEntity?,
     val dt: String?
)

data class CoordEntity(
    val lon: String?,
    val lat: String?,
)
data class DustMainEntity(
    val aqi : String?,
)
data class DustComponentsEntity(
    val co: String?,
    val no: String?,
    val no2: String?,
    val so2: String?,
    val pm2_5: String?,
    val pm10: String?,
    val nh3: String?
)