package com.example.di

import com.example.accounts.data.AccountsRepositoryImpl
import com.example.accounts.domain.repository.AccountsRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal interface RepositoryBinding {

    @Binds
    @Singleton
    fun bindsAccountRepository(accountsRepositoryImpl: AccountsRepositoryImpl) : AccountsRepository
}