package com.scoreplace.hanple.presentation.repository

import com.scoreplace.hanple.Address.AddressResponse
import retrofit2.http.Header
import retrofit2.http.Query

interface AddressRepository {
    suspend fun getAddress(
        Authorization: String,
        query: String?,
    ): AddressResponse
}