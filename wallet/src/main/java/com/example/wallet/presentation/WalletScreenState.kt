package com.example.wallet.presentation

import androidx.compose.runtime.Immutable
import com.example.core.domain.model.CryptoListingsModel
import com.example.wallet.domain.model.CryptoWalletModel

@Immutable
data class WalletScreenState(
    val wallet : CryptoWalletModel = CryptoWalletModel("",false,"",""),
    val myCoins : List<CryptoListingsModel> = emptyList(),
    val myCoinsError : String? = null,
    val myCoinsLoading : Boolean = false,
    val favouriteCoins : List<CryptoListingsModel> = emptyList(),
    val favouriteCoinsError : String? = null,
    val favouriteCoinsLoading : Boolean = false,
)
