package com.example.cryptoscope.di.viewmodel

import androidx.lifecycle.ViewModel
import com.example.accounts.presentation.AccountsViewModel
import com.example.crypto_info.presentation.comparison.CryptoComparisonViewModel
import com.example.cryptolisting.presentation.CryptoListingViewModel
import com.example.currency.presentation.CurrencyRatesViewModel
import com.example.language.LanguageViewModel
import com.example.news.presentation.NewsViewModel
import com.example.settings.presentation.SettingsViewModel
import com.example.wallet.presentation.CryptoWalletViewModel
import com.example.wallet.presentation.my_coins.MyCoinsViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    fun provideSettingsViewModel(settingsViewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CryptoComparisonViewModel::class)
    fun provideCryptoComparisonViewModel(cryptoComparisonViewModel: CryptoComparisonViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AccountsViewModel::class)
    fun provideAccountsViewModel(accountsViewModel: AccountsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MyCoinsViewModel::class)
    fun provideMyCoinsViewModel(myCoinsViewModel: MyCoinsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LanguageViewModel::class)
    fun provideLanguageViewModel(languageViewModel: LanguageViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CurrencyRatesViewModel::class)
    fun provideCurrencyRatesViewModel(currencyRatesViewModel: CurrencyRatesViewModel): ViewModel

}