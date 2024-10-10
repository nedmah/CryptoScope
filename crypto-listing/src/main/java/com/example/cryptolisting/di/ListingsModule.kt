@file:OptIn(ExperimentalPagingApi::class)

package com.example.cryptolisting.di

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.core.db.CryptoDb
import com.example.core.db.daos.CryptoListingsDao
import com.example.core.db.daos.FavouritesDao
import com.example.core.db.entities.CryptoListingEntity
import com.example.cryptolisting.data.remote.CryptoListingsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn (SingletonComponent::class)
object ListingsModule {


    @Provides
    @Singleton
    fun provideCryptoApi(retrofit: Retrofit): CryptoListingsApi {
        return retrofit.create(CryptoListingsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideListingsDao(db: CryptoDb) : CryptoListingsDao = db.getCryptoListingsDao()

    @Provides
    @Singleton
    fun provideFavouritesDao(db: CryptoDb) : FavouritesDao = db.getFavouritesDao()

}