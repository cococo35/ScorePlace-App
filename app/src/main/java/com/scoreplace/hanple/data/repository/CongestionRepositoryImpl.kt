package com.scoreplace.hanple.data.repository

import com.scoreplace.hanple.data.remote.CongestionRemoteDataResource
import com.scoreplace.hanple.domain.remote.toEntity
import javax.inject.Inject


class CongestionRepositoryImpl @Inject constructor(
    private val congestionRemoteDataResource: CongestionRemoteDataResource
) : CongestionRepository {
    override suspend fun getCongestion(
        KEY: String?,
        TYPE: String?,
        SERVICE: String?,
        START_INDEX: Int,
        END_INDEX: Int,
        AREA_NM: String
    ) = congestionRemoteDataResource.getCongestion(KEY, TYPE, SERVICE, START_INDEX, END_INDEX, AREA_NM).toEntity()
}