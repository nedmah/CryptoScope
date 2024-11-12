package com.example.core.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WalletCryptoEntity(
    @PrimaryKey
    val id: Int,
    val cryptoId : String,
    val count : String,
    val amount: String
)

data class WalletWithDetails(
    val id: Int,
    val cryptoId: String,
    val amount: String,
    val count: String,
    val name: String,
    val symbol : String,
    val price: String,
    val percentage: String
)