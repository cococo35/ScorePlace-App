package com.scoreplace.hanple.data.remote

import com.scoreplace.hanple.data.congestion.CongestionResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CongestionRemoteDataResource {
    @GET("{KEY}/{TYPE}/{SERVICE}/{START_INDEX}/{END_INDEX}/{AREA_NM}")
    suspend fun getCongestion(
        @Path("KEY") KEY: String?,
        @Path("TYPE") TYPE: String?,
        @Path("SERVICE") SERVICE: String?,
        @Path("START_INDEX")START_INDEX: Int,
        @Path("END_INDEX")END_INDEX: Int,
        @Path("AREA_NM") AREA_NM: String
    ) : CongestionResponse
}

