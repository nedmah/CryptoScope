package com.example.cryptolisting.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CryptoListingsModel(
    val rank : Int,
    val symbol : String,
    val cryptoId : String,
    val name : String,
    val icon : String,
    val price : String,
    val percentage : String
) : Parcelable
