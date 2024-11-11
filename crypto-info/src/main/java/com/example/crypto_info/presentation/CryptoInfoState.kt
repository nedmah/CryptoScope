package com.example.crypto_info.presentation

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.example.crypto_info.domain.model.CryptoInfo

@Immutable
data class CryptoInfoState(
    val name : String = "",
    val price : String = "",
    val icon : String = "",
    val cryptoId : String = "",
    val percentage : String = "",
    val cryptoInfo : CryptoInfo = CryptoInfo(emptyList(), emptyList()),
    val loading : Boolean = false,
    val currentInterval : TimeIntervals = TimeIntervals.ONE_DAY,
    val totalSupply : String = "",
    val marketCap : String = "",
    val redditUrl : String = "",
    val twitterUrl : String = "",
    val isFavourite: Boolean = false,
    val error : String = ""
)
