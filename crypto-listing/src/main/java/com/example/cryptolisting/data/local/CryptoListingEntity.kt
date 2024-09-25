package com.example.cryptolisting.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CryptoListingEntity(
    @PrimaryKey
    val id: Int,
    val cryptoId : String,
    val iconUrl: String,
    val name: String,
    val symbol: String,
    val price: String,
    val percentage: String,
    val percentageOneHour : String,
    val percentageOneWeek : String,
    val totalSupply : String,
    val marketCap : String,
    val redditUrl : String,
    val twitterUrl : String
)
