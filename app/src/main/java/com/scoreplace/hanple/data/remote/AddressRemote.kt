package com.scoreplace.hanple.data.remote

import com.scoreplace.hanple.Address.AddressResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
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




