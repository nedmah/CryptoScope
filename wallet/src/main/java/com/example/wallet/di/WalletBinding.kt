package com.example.wallet.di


import com.example.wallet.data.local.data_source.FavoriteCoinsDatasourceImpl
import com.example.wallet.data.repository.WalletRepositoryImpl
import com.example.wallet.domain.data_source.FavoriteCoinsDatasource
import com.example.wallet.domain.WalletRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal interface WalletBinding {



    @Binds
    @Singleton
    fun bindFavoriteCoinsDatasource(datasource: FavoriteCoinsDatasourceImpl) : FavoriteCoinsDatasource

    @Binds
    @Singleton
    fun bindWalletRepo(repository: WalletRepositoryImpl) : WalletRepository


}