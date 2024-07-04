package com.scoreplace.hanple.presentation.repository

import com.scoreplace.hanple.Address.AddressResponse
import com.scoreplace.hanple.domain.remote.AddressResponseEntity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton




interface AddressRepository {

    suspend fun getAddress(
        Authorization: String,
        query: String?,
    ): AddressResponseEntity
}