package com.example.wallet.di

import com.example.core.data.db.CryptoDb
import com.example.core.data.db.daos.CryptoListingsDao
import com.example.core.data.db.daos.WalletBalanceDao
import com.example.core.data.db.daos.WalletCryptoDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [
    DatasourceBinding::class
])
object WalletModule {

    @Provides
    @Singleton
    fun provideWalletBalanceDao(db: CryptoDb) : WalletBalanceDao = db.getWalletBalanceDao()

    @Provides
    @Singleton
    fun provideWalletCryptoDao(db: CryptoDb) : WalletCryptoDao = db.getWalletCryptoDao()
}