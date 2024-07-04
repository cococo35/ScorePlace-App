package com.scoreplace.hanple.di

import com.scoreplace.hanple.data.remote.AddressRemoteDataResource
import com.scoreplace.hanple.data.repository.AddressRepositoryImpl
import com.scoreplace.hanple.presentation.repository.AddressRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@InstallIn(ViewModelComponent::class)
@Module
abstract class AddressBindModule  {

    @ViewModelScoped
    @Binds
    abstract fun bindAddressRepository(
        repository: AddressRepositoryImpl
    ) : AddressRepository
}