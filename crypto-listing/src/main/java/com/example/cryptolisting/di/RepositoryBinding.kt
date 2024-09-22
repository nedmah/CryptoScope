package com.example.cryptolisting.di

import com.example.cryptolisting.data.repository.CryptoListingRepositoryImpl
import com.example.cryptolisting.domain.repository.CryptoListingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryBinding {

    @Binds
    @Singleton
    fun bindListingRepository(repositoryImpl: CryptoListingRepositoryImpl) : CryptoListingsRepository
}