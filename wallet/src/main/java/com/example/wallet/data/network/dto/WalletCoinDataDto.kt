package com.example.wallet.data.network.dto

import com.squareup.moshi.Json

data class WalletCoinDataDto(
    val blockchain : String?,
    val balances : List<WalletCoinDto>?
)


data class WalletCoinDto(

    val coinId : String,

    val amount : Double,

    val chain : String,

    val name : String,

    val symbol : String,

    val price : String,

    val imgUrl : String,

    @field:Json(name = "pCh24h")
    val percentOneDay : Double,

    val rank : Int,

    val volume : String
)
