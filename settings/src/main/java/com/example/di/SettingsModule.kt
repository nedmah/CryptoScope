package com.example.di

import com.example.accounts.data.network.blockchainApi
import com.example.core.data.db.CryptoDb
import com.example.core.data.db.daos.AccountsDao
import com.example.core.data.db.daos.BlockchainsDao
import com.example.core.data.db.daos.WalletCryptoDao
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [RepositoryBinding::class])
object SettingsModule {

    @Provides
    @Singleton
    fun provideAccountsApi(retrofit: Retrofit) : blockchainApi{
        return retrofit.create(blockchainApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAccountsDao(db: CryptoDb) : AccountsDao = db.getAccountDao()

    @Provides
    @Singleton
    fun provideBlockchainsDao(db: CryptoDb) : BlockchainsDao = db.getBlockchainsDao()
}