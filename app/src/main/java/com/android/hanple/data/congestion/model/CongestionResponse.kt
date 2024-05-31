package com.android.hanple.data.congestion.model

import com.google.gson.annotations.SerializedName

data class CongestionResponse(
    @SerializedName("SeoulRtd.citydata_ppltn") val seoulRtdCitydataPpltn : List<CityData>?,
    @SerializedName("RESULT") val result: Result?,
)

data class CityData(
    @SerializedName("AREA_CONGEST_LVL") val areaCongestLv: String?,
    @SerializedName("AREA_CONGEST_MSG") val areaCongestMsg: String?,
    @SerializedName("PPLTN_TIME") val pplTnTime: String?,
    @SerializedName("FCST_PPLTN") val fcstPpltn: List<FcstData>?,
)

data class FcstData(
    @SerializedName("FCST_TIME") val fcstTime: String?,
    @SerializedName("FCST_CONGEST_LVL") val fcstCongestLv: String?,
)
data class Result(
    val resultCode: String?,
    val resultMessage: String?,
)