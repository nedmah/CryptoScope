package com.example.wallet.presentation

import androidx.compose.runtime.Immutable
import com.example.core.domain.model.CryptoListingsModel
import com.example.wallet.domain.model.MyCoinsModel
import com.example.wallet.domain.model.CryptoWalletModel

@Immutable
data class WalletScreenState(
    val wallet : CryptoWalletModel = CryptoWalletModel("","",""),
    val myCoins : List<MyCoinsModel> = emptyList(),
    val myCoinsError : String? = null,
    val currentAddress : String? = null,
    val currentBlockchain : String? = null,
    val currentBlockchainImage : String? = null,
    val myCoinsLoading : Boolean = false,
    val favouriteCoins : List<CryptoListingsModel> = emptyList(),
    val favouriteCoinsError : String? = null,
)
