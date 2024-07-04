package com.scoreplace.hanple.data.repository

import com.scoreplace.hanple.Address.AddressRemoteDataResource

import com.scoreplace.hanple.Address.AddressResponse

import com.scoreplace.hanple.presentation.repository.AddressRepository

class AddressRemoteImpl(private val addressRemoteDataResource: AddressRemoteDataResource) :
    AddressRepository {
    override suspend fun getAddress(Authorization: String, query: String?): AddressResponse = addressRemoteDataResource.getAddress(Authorization, query)

}