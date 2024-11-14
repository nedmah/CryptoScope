package com.example.wallet.di


import com.example.wallet.data.local.data_source.FavoriteCoinsDatasourceImpl
import com.example.wallet.data.local.data_source.MyCoinsListingsDatasourceImpl
import com.example.wallet.data.local.data_source.WalletBalanceDatasourceImpl
import com.example.wallet.domain.data_source.FavoriteCoinsDatasource
import com.example.wallet.domain.data_source.MyCoinsListingsDatasource
import com.example.wallet.domain.data_source.WalletBalanceDatasource
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal interface DatasourceBinding {

    @Binds
    @Singleton
    fun bindMyCoinsListingsDatasource(datasource: MyCoinsListingsDatasourceImpl) : MyCoinsListingsDatasource

    @Binds
    @Singleton
    fun bindFavoriteCoinsDatasource(datasource: FavoriteCoinsDatasourceImpl) : FavoriteCoinsDatasource

    @Binds
    @Singleton
    fun bindWalletBalanceDatasource(datasource: WalletBalanceDatasourceImpl) : WalletBalanceDatasource
}