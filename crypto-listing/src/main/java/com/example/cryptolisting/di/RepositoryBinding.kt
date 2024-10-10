package com.example.cryptolisting.di

import com.example.cryptolisting.data.repository.CryptoListingRepositoryImpl
import com.example.cryptolisting.data.repository.FavouritesRepositoryImpl
import com.example.cryptolisting.domain.repository.CryptoListingsRepository
import com.example.cryptolisting.domain.repository.FavouritesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryBinding {

    @Binds
    @Singleton
    fun bindListingRepository(repositoryImpl: CryptoListingRepositoryImpl) : CryptoListingsRepository

    @Binds
    @Singleton
    fun bindFavouritesRepository(repositoryImpl: FavouritesRepositoryImpl) : FavouritesRepository
}