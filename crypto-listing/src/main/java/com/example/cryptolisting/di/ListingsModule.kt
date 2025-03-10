package com.example.cryptolisting.di

import com.example.core.data.db.CryptoDb
import com.example.core.data.db.daos.CryptoListingsDao
import com.example.core.data.db.daos.FavouritesDao
import com.example.cryptolisting.data.remote.CryptoListingsApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [RepositoryBinding::class])
object ListingsModule {

    @Provides
    @Singleton
    fun provideCryptoApi(retrofit: Retrofit): CryptoListingsApi {
        return retrofit.create(CryptoListingsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFavouritesDao(db: CryptoDb) : FavouritesDao = db.getFavouritesDao()

}