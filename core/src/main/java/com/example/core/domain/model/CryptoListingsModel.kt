package com.example.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class CryptoListingsModel(
    val rank : Int,
    val symbol : String,
    val cryptoId : String,
    val name : String,
    val icon : String,
    val price : String,
    val percentage : String,
    val percentageOneHour : String,
    val percentageOneWeek : String,
    val totalSupply : String,
    val marketCap : String,
    val redditUrl : String,
    val twitterUrl : String,
    val isFavorite: Boolean = false
) : Parcelable
