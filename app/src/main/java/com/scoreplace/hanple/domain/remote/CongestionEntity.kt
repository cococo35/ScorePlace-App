package com.scoreplace.hanple.domain.remote

data class CongestionResponseEntity(
    val seoulRtdCitydataPpltn : List<CityDataEntity>?,
    val result: ResultEntity?,
)

data class CityDataEntity(
    val areaCongestLv: String?,
    val areaCongestMsg: String?,
    val pplTnTime: String?,
    val fcstPpltn: List<FcstDataEntity>?,
)

data class FcstDataEntity(
    val fcstTime: String?,
    val fcstCongestLv: String?,
)
data class ResultEntity(
    val resultCode: String?,
    val resultMessage: String?,
)