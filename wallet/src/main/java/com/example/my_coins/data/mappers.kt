package com.example.my_coins.data

import com.example.core.data.db.entities.WalletWithDetails
import com.example.my_coins.domain.model.MyCoinsModel

fun WalletWithDetails.toMyCoinsModel() : MyCoinsModel{
    return MyCoinsModel(
        id, cryptoId, amount, count, name, symbol, price, percentage
    )
}