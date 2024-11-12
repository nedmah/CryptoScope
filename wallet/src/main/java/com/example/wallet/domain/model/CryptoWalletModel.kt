package com.example.wallet.domain.model

data class CryptoWalletModel(
    val balance : String,
    val isAddition : Boolean,
    val profitOrAddition : String,
    val percentage : String
)
