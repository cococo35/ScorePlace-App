package com.scoreplace.hanple.data.repository

import com.scoreplace.hanple.data.congestion.CongestionRemoteDataResource



class CongestionRemoteImpl(
    private val congestionRemoteDataResource: CongestionRemoteDataResource
) : CongestionRepository {
    override suspend fun getCongestion(
        KEY: String?,
        TYPE: String?,
        SERVICE: String?,
        START_INDEX: Int,
        END_INDEX: Int,
        AREA_NM: String
    ) = congestionRemoteDataResource.getCongestion(KEY, TYPE, SERVICE, START_INDEX, END_INDEX, AREA_NM)
}