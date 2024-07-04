package com.scoreplace.hanple.di

import com.scoreplace.hanple.data.repository.CongestionRepository
import com.scoreplace.hanple.data.repository.CongestionRepositoryImpl
import com.scoreplace.hanple.data.repository.DustRepositoryImpl
import com.scoreplace.hanple.presentation.repository.DustRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@InstallIn(ViewModelComponent::class)
@Module
abstract class CongestionBindModule {

    @ViewModelScoped
    @Binds
    abstract fun bindCongestionRepository(
        repository: CongestionRepositoryImpl
    ) : CongestionRepository
}