package com.scoreplace.hanple.Address

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface AddressRemoteDataResource {

    @GET("/v2/local/search/keyword.json")
    suspend fun getAddress(
        @Header("Authorization") Authorization : String,
        @Query("query") query : String?,
    ) : AddressResponse
}




