package com.example.di

import android.content.Context
import com.example.accounts.data.network.BlockchainApi
import com.example.core.data.db.CryptoDb
import com.example.core.data.db.daos.AccountsDao
import com.example.core.data.db.daos.BlockchainsDao
import com.example.currency.data.remote.CurrencyRatesApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [SettingsBinding::class])
object SettingsModule {

    @Provides
    @Singleton
    fun provideAccountsApi(retrofit: Retrofit) : BlockchainApi{
        return retrofit.create(BlockchainApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCurrenciesApi(retrofit: Retrofit) : CurrencyRatesApi{
        return retrofit.create(CurrencyRatesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAccountsDao(db: CryptoDb) : AccountsDao = db.getAccountDao()

    @Provides
    @Singleton
    fun provideBlockchainsDao(db: CryptoDb) : BlockchainsDao = db.getBlockchainsDao()
}