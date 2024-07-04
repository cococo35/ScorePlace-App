package com.scoreplace.hanple.di

import com.scoreplace.hanple.data.repository.AddressRepositoryImpl
import com.scoreplace.hanple.data.repository.DustRepositoryImpl
import com.scoreplace.hanple.presentation.repository.AddressRepository
import com.scoreplace.hanple.presentation.repository.DustRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
abstract class DustBindModule {

    @ViewModelScoped
    @Binds
    abstract fun bindDustRepository(
        repository: DustRepositoryImpl
    ) : DustRepository
}