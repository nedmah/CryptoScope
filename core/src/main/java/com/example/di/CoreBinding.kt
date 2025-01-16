package com.example.di

import android.app.Application
import android.content.Context
import com.example.core.data.repository.FavouritesRepositoryImpl
import com.example.core.data.settings.SettingsDataStoreImpl
import com.example.core.domain.repository.FavouritesRepository
import com.example.core.domain.settings.SettingsDataStore
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal interface CoreBinding {

    @Binds
    @Singleton
    fun bindFavouritesRepository(repositoryImpl: FavouritesRepositoryImpl) : FavouritesRepository

}