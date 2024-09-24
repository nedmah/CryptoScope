@file:OptIn(ExperimentalPagingApi::class)

package com.example.cryptolisting.di

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.cryptolisting.data.local.CryptoDb
import com.example.cryptolisting.data.local.CryptoListingEntity
import com.example.cryptolisting.data.remote.CryptoListingsApi
import com.example.cryptolisting.data.remote.ListingsRemoteMediator
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
    fun provideDb(app: Application) : CryptoDb {
        return Room.databaseBuilder(app, CryptoDb::class.java,"cryptoDb.db").build()
    }

    @Provides
    @Singleton
    fun provideCryptoPager(db: CryptoDb, api: CryptoListingsApi) : Pager<Int, CryptoListingEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = ListingsRemoteMediator(api, db),
            pagingSourceFactory = {db.getCryptoListingsDao().pagingSource()}
        )
    }
}