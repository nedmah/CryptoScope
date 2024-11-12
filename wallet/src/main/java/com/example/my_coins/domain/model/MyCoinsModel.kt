package com.example.my_coins.domain.model

data class MyCoinsModel(
    val id: Int,
    val cryptoId: String,
    val amount: String,
    val count: String,
    val name: String,
    val symbol : String,
    val price: String,
    val percentage: String
)
