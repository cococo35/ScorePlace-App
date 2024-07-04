package com.scoreplace.hanple.data.repository

import com.scoreplace.hanple.Dust.DustApiResponse
import com.scoreplace.hanple.data.remote.DustRemoteDataSource
import com.scoreplace.hanple.domain.remote.toEntity
import com.scoreplace.hanple.presentation.repository.DustRepository
import javax.inject.Inject


class DustRepositoryImpl @Inject constructor(private val dustRemoteDataSource : DustRemoteDataSource) : DustRepository {
    override suspend fun getDustData(lat: String, lon: String, appid: String) =
        dustRemoteDataSource.getDustData(lat, lon, appid).toEntity()
}