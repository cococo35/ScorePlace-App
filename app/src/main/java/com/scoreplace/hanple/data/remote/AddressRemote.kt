package com.scoreplace.hanple.Address

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface AddressRemote {

    @GET("/v2/local/search/keyword.json")
    suspend fun getAddress(
        @Header("Authorization") Authorization : String,
        @Query("query") query : String?,
    ) : AddressResponse
}

class AddressRemoteImpl(
    private val addressRemote: AddressRemote
) : AddressRemote {
    override suspend fun getAddress(
        Authorization: String,
        query: String?,
    ) = addressRemote.getAddress(Authorization, query)

}


