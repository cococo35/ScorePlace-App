package com.android.hanple.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object ConvertUtils {

    //minSdk = 24, targetSdk = 34
    //참고한 자료: https://stackoverflow.com/questions/47250263/kotlin-convert-timestamp-to-datetime
    @RequiresApi(Build.VERSION_CODES.O) //API level 26 이상
    fun unixTimeConverter(unixTime: Long): LocalDateTime = Instant.ofEpochSecond(unixTime)
            .atZone(ZoneId.systemDefault()) //안드로이드 OS가 확인한 현재 시간대.
            .toLocalDateTime()


    val phoneNumberRegex = "^\\+?\\d{1,4}[- ]?\\d{4,}(?:[- ]?\\d{4,})?\$".toRegex() //SignUpViewModel에 있는 값을 옮김.
}