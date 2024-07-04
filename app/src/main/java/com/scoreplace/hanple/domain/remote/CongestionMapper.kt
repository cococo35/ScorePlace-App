package com.scoreplace.hanple.domain.remote

import com.scoreplace.hanple.data.congestion.CityData
import com.scoreplace.hanple.data.congestion.CongestionResponse
import com.scoreplace.hanple.data.congestion.FcstData
import com.scoreplace.hanple.data.congestion.Result


fun CongestionResponse.toEntity() = CongestionResponseEntity(
    seoulRtdCitydataPpltn = seoulRtdCitydataPpltn?.map { it ->
        it.toEntity()
    },
    result = result?.toEntity()
)

fun CityData.toEntity() = CityDataEntity(
    areaCongestLv = areaCongestLv,
    areaCongestMsg = areaCongestMsg,
    pplTnTime = pplTnTime,
    fcstPpltn = fcstPpltn?.map { it ->
        it.toEntity()
    }
)

fun FcstData.toEntity() = FcstDataEntity(
    fcstTime = fcstTime,
    fcstCongestLv = fcstCongestLv
)

fun Result.toEntity() = ResultEntity(
    resultCode = resultCode,
    resultMessage = resultMessage

)

