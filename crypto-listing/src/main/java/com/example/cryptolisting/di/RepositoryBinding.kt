package com.example.cryptolisting.di

import com.example.cryptolisting.data.repository.CryptoListingRepositoryImpl
import com.example.core.data.repository.FavouritesRepositoryImpl
import com.example.cryptolisting.domain.repository.CryptoListingsRepository
import com.example.core.domain.repository.FavouritesRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal interface RepositoryBinding {

    @Binds
    @Singleton
    fun bindListingRepository(repositoryImpl: CryptoListingRepositoryImpl) : CryptoListingsRepository

}