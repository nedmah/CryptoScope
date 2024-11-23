package com.example.news.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.example.core.data.db.CryptoDb
import com.example.core.data.db.entities.CryptoNewsEntity
import com.example.news.data.remote.CryptoNewsApi
import com.example.news.data.remote.CryptoNewsPager
import com.example.news.data.remote.CryptoNewsRemoteMediator
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
object NewsModule {


    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit) : CryptoNewsApi{
        return retrofit.create(CryptoNewsApi::class.java)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideNewsPager(db: CryptoDb, api: CryptoNewsApi) : CryptoNewsPager {
        return CryptoNewsPager(pager = Pager(
            config = PagingConfig(20),
            remoteMediator = CryptoNewsRemoteMediator(db,api),
            pagingSourceFactory = { db.getCryptoNewsDao().pagingSource() }
        ))
    }
}