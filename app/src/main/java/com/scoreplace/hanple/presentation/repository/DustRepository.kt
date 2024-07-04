package com.scoreplace.hanple.presentation.repository

import com.scoreplace.hanple.Dust.DustApiResponse

interface DustRepository {
    suspend fun getDustData(lat: String, lon: String, appid: String) : DustApiResponse
}