package com.scoreplace.hanple.Dust

import com.google.gson.annotations.SerializedName

data class DustApiResponse(
    @SerializedName("coord") val coord: Coord?,
    @SerializedName("list") val list: List<DustResponse>?
)

data class DustResponse(
    @SerializedName("main") val main: DustMain?,
    @SerializedName("components") val components: DustComponents?,
    @SerializedName("dt") val dt: String?
)

data class Coord(
    val lon: String?,
    val lat: String?,
)
data class DustMain(
    val aqi : String?,
)
data class DustComponents(
    val co: String?,
    val no: String?,
    val no2: String?,
    val so2: String?,
    val pm2_5: String?,
    val pm10: String?,
    val nh3: String?
)

