package com.example.cryptolisting.data.remote

import com.squareup.moshi.Json

data class CryptoListingDataDto(
    val id: String,

    val icon : String,

    val name : String,

    val symbol : String,

    val price : Double,

    val rank : Int,

    @field:Json(name = "priceChange1d")
    val percentage : String
)