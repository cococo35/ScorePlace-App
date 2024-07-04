package com.scoreplace.hanple.data.repository

import com.scoreplace.hanple.data.congestion.CongestionResponse
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent



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