package com.example.wallet.data

import com.example.core.data.db.entities.WalletBalanceEntity
import com.example.core.data.db.entities.WalletWithDetails
import com.example.wallet.domain.model.CryptoWalletModel

fun WalletBalanceEntity.toCryptoWalletModel() : CryptoWalletModel{
    return CryptoWalletModel(
        balance = totalBalance,
        isAddition = isAddition,
        profitOrAddition = profitOrAddition,
        percentage = percentageChange
    )
}

