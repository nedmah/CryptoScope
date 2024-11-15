package com.example.wallet.data

import com.example.core.data.db.entities.WalletBalanceEntity
import com.example.core.data.db.entities.WalletWithDetails
import com.example.core.util.formatTimestamp
import com.example.wallet.domain.model.CryptoWalletModel
import com.example.wallet.domain.model.WalletHistoryModel

fun WalletBalanceEntity.toCryptoWalletModel() : CryptoWalletModel{
    return CryptoWalletModel(
        balance = totalBalance,
        isAddition = isAddition,
        profitOrAddition = profitOrAddition,
        percentage = percentageChange
    )
}

fun WalletBalanceEntity.toWalletHistoryModel() : WalletHistoryModel{
    val formattedDate = formatTimestamp(date)
    return WalletHistoryModel(
        totalBalance = totalBalance,
        profitOrAddition = profitOrAddition,
        isAddition = isAddition,
        percentageChange = percentageChange,
        date = formattedDate
    )
}