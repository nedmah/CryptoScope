package com.example.wallet.di

import com.example.core.data.db.CryptoDb
import com.example.core.data.db.daos.CryptoListingsDao
import com.example.core.data.db.daos.WalletBalanceDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object WalletModule {


    @Provides
    @Singleton
    fun provideListingsDao(db: CryptoDb) : CryptoListingsDao = db.getCryptoListingsDao()

    @Provides
    @Singleton
    fun provideWalletBalanceDao(db: CryptoDb) : WalletBalanceDao = db.getWalletBalanceDao()
}