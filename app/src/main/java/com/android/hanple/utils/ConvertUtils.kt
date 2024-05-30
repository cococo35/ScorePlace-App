package com.android.hanple.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object ConvertUtils {
    @RequiresApi(Build.VERSION_CODES.O)
    fun unixTimeConverter(unixTime: Long): LocalDateTime = Instant.ofEpochSecond(unixTime)
            .atZone(ZoneId.systemDefault()) //안드로이드 OS가 확인한 현재 시간대.
            .toLocalDateTime()
}