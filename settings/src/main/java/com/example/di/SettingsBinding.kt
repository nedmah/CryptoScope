package com.example.di

import com.example.accounts.data.AccountsRepositoryImpl
import com.example.accounts.domain.repository.AccountsRepository
import com.example.currency.data.remote.CurrencyDataSourceImpl
import com.example.currency.domain.CurrencyDataSource
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal interface SettingsBinding {

    @Binds
    @Singleton
    fun bindsAccountRepository(accountsRepositoryImpl: AccountsRepositoryImpl) : AccountsRepository

    @Binds
    @Singleton
    fun bindCurrencyDataSource(currencyDataSourceImpl: CurrencyDataSourceImpl) : CurrencyDataSource
}