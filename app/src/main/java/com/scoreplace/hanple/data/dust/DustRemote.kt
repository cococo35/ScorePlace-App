package com.scoreplace.hanple.Dust

import retrofit2.http.GET
import retrofit2.http.Query

interface DustRemote {
    @GET("forecast")
    suspend fun getDustData(
        @Query("lat") lat : String,
        @Query("lon") lon : String,
        @Query("appid") appid : String,
    ) : DustApiResponse
}

class DustRemoteImpl(
    private val dustRemote: DustRemote
) : DustRemote {
    override suspend fun getDustData(
        lat: String, lon: String, appid: String) = dustRemote.getDustData(lat, lon, appid)
}