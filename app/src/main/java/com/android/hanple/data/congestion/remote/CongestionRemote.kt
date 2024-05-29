package com.android.hanple.data.congestion.remote

import com.android.hanple.data.congestion.model.CongestionResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CongestionRemote {
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


class CongestionRemoteImpl(
    private val congestionRemote: CongestionRemote
) : CongestionRemote{
    override suspend fun getCongestion(
        KEY: String?,
        TYPE: String?,
        SERVICE: String?,
        START_INDEX: Int,
        END_INDEX: Int,
        AREA_NM: String
    ) = congestionRemote.getCongestion(KEY, TYPE, SERVICE, START_INDEX, END_INDEX, AREA_NM)

}