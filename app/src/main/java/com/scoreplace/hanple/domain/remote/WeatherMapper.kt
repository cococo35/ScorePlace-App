package com.scoreplace.hanple.domain.remote

import com.scoreplace.hanple.Weather.Cloud
import com.scoreplace.hanple.Weather.Sys
import com.scoreplace.hanple.Weather.Weather
import com.scoreplace.hanple.Weather.WeatherApiResponse
import com.scoreplace.hanple.Weather.WeatherMain
import com.scoreplace.hanple.Weather.WeatherResponse
import com.scoreplace.hanple.Weather.Wind


fun WeatherApiResponse.toEntity() = WeatherApiResponseEntity(
    cod = cod,
    message = message,
    cnt = cnt,
    list = list?.map { it ->
        it.toEntity()
    }
)

fun WeatherResponse.toEntity() = WeatherResponseEntity(
    dt = dt,
    main = main?.toEntity(),
    weather = weather.map { it ->
        it.toEntity()
    },
    cloud = cloud?.toEntity(),
    wind = wind?.toEntity(),
    visibility = visibility,
    pop = pop,
    sys = sys?.toEntity(),
    dt_txt = dt_txt
)

fun WeatherMain.toEntity() = WeatherMainEntity(
    temp =  temp,
    feels_like = feels_like,
    temp_min = temp_min,
    temp_max =  temp_max,
    pressure =  pressure,
    sea_level = sea_level,
    grnd_level = grnd_level,
    humidity = humidity,
    temp_kf = temp_kf
)

fun Cloud.toEntity() = CloudEntity(
    all = all
)

fun Wind.toEntity() = WindEntity(
    speed = speed,
    deg = deg,
    gust = gust
)

fun Sys.toEntity() = SysEntity(
    pod = pod
)

fun Weather.toEntity() = WeatherEntity(
    id = id,
    main = main,
    description = description,
    icon = icon
)
