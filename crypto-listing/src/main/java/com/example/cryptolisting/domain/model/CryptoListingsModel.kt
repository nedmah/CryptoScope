package com.example.cryptolisting.domain.model

data class CryptoListingsModel(
    val rank : Int,
    val symbol : String,
    val cryptoId : String,
    val name : String,
    val icon : String,
    val price : String,
    val percentage : String
)
