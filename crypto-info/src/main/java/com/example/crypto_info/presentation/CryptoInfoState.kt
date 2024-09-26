package com.example.crypto_info.presentation

import com.example.crypto_info.domain.model.CryptoInfo

data class CryptoInfoState(
    val name : String = "",
    val price : String = "",
    val icon : String = "",
    val percentage : String = "",
    val cryptoInfo : CryptoInfo = CryptoInfo(emptyList(), emptyList()),
    val loading : Boolean = false,
    val currentInterval : TimeIntervals = TimeIntervals.ONE_DAY,
    val totalSupply : String = "",
    val marketCap : String = "",
    val redditUrl : String = "",
    val twitterUrl : String = "",
    val error : String = ""
)
