package com.scoreplace.hanple.domain.remote

import com.google.gson.annotations.SerializedName
import com.scoreplace.hanple.Dust.Coord
import com.scoreplace.hanple.Dust.DustApiResponse
import com.scoreplace.hanple.Dust.DustComponents
import com.scoreplace.hanple.Dust.DustMain
import com.scoreplace.hanple.Dust.DustResponse


fun DustApiResponse.toEntity() = DustApiResponseEntity(
    coord = coord?.toEntity(),
    list = list?.map { it ->
        it.toEntity()
    }
)

fun DustResponse.toEntity() = DustResponseEntity(
    main = main?.toEntity(),
    components = components?.toEntity(),
    dt = dt
)

fun Coord.toEntity() = CoordEntity(
    lon = lon,
    lat = lat,
)

fun DustMain.toEntity() = DustMainEntity(
    aqi = aqi
)

fun DustComponents.toEntity() = DustComponentsEntity(
    co = co,
    no = no,
    no2 = no2,
    so2 = so2,
    pm2_5 = pm2_5,
    pm10 = pm10,
    nh3 = nh3,
)
