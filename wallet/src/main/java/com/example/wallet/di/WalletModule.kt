package com.example.wallet.di

import com.example.core.data.db.CryptoDb
import com.example.core.data.db.daos.AccountsDao
import com.example.core.data.db.daos.BlockchainsDao
import com.example.core.data.db.daos.MyCoinsDao
import com.example.core.data.db.daos.WalletChartDao
import com.example.wallet.data.network.WalletApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [
    WalletBinding::class
])
object WalletModule {

    @Provides
    @Singleton
    fun provideMyCoinsDao(db: CryptoDb) : MyCoinsDao = db.getMyCoinsDao()

    @Provides
    @Singleton
    fun provideWalletChartDao(db: CryptoDb) : WalletChartDao = db.getWalletChartDao()


    @Provides
    @Singleton
    fun provideWalletApi(retrofit: Retrofit) : WalletApi = retrofit.create(WalletApi::class.java)

}