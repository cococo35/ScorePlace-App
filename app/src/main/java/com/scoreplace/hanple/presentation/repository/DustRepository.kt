package com.scoreplace.hanple.presentation.repository

import com.scoreplace.hanple.Dust.DustApiResponse
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent



interface DustRepository {

    suspend fun getDustData(lat: String, lon: String, appid: String) : DustApiResponse
}