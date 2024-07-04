package com.scoreplace.hanple.data.repository

import com.scoreplace.hanple.data.congestion.CongestionResponse

interface CongestionRepository {
    suspend fun getCongestion(
        KEY: String?,
        TYPE: String?,
        SERVICE: String?,
        START_INDEX: Int,
        END_INDEX: Int,
        AREA_NM: String
    ) : CongestionResponse
}