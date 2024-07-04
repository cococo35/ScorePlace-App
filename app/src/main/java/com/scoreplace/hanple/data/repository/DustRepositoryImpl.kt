package com.scoreplace.hanple.data.repository

import com.scoreplace.hanple.Dust.DustApiResponse
import com.scoreplace.hanple.data.remote.DustRemoteDataSource
import com.scoreplace.hanple.presentation.repository.DustRepository


class DustRepositoryImpl(private val dustRemoteDataSource : DustRemoteDataSource) : DustRepository {
    override suspend fun getDustData(lat: String, lon: String, appid: String): DustApiResponse {
        return dustRemoteDataSource.getDustData(lat, lon, appid)
    }
}