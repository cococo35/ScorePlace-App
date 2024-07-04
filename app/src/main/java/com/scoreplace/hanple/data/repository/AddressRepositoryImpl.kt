package com.scoreplace.hanple.data.repository

import com.scoreplace.hanple.data.remote.AddressRemoteDataResource

import com.scoreplace.hanple.Address.AddressResponse
import com.scoreplace.hanple.domain.remote.toEntity

import com.scoreplace.hanple.presentation.repository.AddressRepository
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(private val addressRemoteDataResource: AddressRemoteDataResource) :
    AddressRepository {
    override suspend fun getAddress(Authorization: String, query: String?) = addressRemoteDataResource.getAddress(Authorization, query).toEntity()
}