package com.example.cryptoscope.di.viewmodel

import androidx.lifecycle.ViewModel
import com.example.crypto_info.presentation.CryptoInfoViewModel
import com.example.cryptolisting.presentation.CryptoListingViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface AppBindsModule {

    @Binds
    @IntoMap
    @ViewModelKey(CryptoListingViewModel::class)
    fun provideCryptoListingViewModel(cryptoListingViewModel: CryptoListingViewModel): ViewModel

//    @Binds
//    @IntoMap
//    @ViewModelKey(CryptoInfoViewModel::class)
//    fun provideCryptoInfoViewModelViewModel(cryptoInfoViewModel: CryptoInfoViewModel): ViewModel

}