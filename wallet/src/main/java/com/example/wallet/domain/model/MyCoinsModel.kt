package com.example.wallet.domain.model

data class MyCoinsModel(
    val coinId : String,
    val amount : Double,
    val chain : String,
    val name : String,
    val symbol : String,
    val price : String,
    val imgUrl : String,
    val percentOneDay : Double,
    val rank : Int,
    val volume : String
)
