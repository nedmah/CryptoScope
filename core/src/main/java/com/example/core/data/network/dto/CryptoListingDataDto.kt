package com.example.core.data.network.dto

import com.squareup.moshi.Json

data class CryptoListingDataDto(
    val id: String,

    val icon : String,

    val name : String,

    val symbol : String,

    val price : String,

    val rank : Int,

    @field:Json(name = "priceChange1d")
    val percentage : String,

    @field:Json(name = "priceChange1h")
    val percentageOneHour : Double,

    @field:Json(name = "priceChange1w")
    val percentageOneWeek : Double,

    val totalSupply : Long,

    val marketCap : String,

    val redditUrl : String?,

    val twitterUrl : String?
)