package com.example.di

import com.example.core.data.repository.FavouritesRepositoryImpl
import com.example.core.domain.repository.FavouritesRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal interface RepositoryBinding {

    @Binds
    @Singleton
    fun bindFavouritesRepository(repositoryImpl: FavouritesRepositoryImpl) : FavouritesRepository
}