package com.example.cryptoscope.di.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cryptolisting.presentation.CryptoListingViewModel
import com.example.news.presentation.NewsViewModel
import com.example.wallet.presentation.CryptoWalletViewModel
import com.example.wallet.presentation.wallet_history.WalletHistoryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface AppBindsModule {

    @Binds
    @IntoMap
    @ViewModelKey(CryptoListingViewModel::class)
    fun provideCryptoListingViewModel(cryptoListingViewModel: CryptoListingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CryptoWalletViewModel::class)
    fun provideWalletViewModel(cryptoWalletViewModel: CryptoWalletViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WalletHistoryViewModel::class)
    fun provideWalletHistoryViewModel(walletHistoryViewModel: WalletHistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel::class)
    fun provideNewsViewModel(newsViewModel: NewsViewModel): ViewModel

}