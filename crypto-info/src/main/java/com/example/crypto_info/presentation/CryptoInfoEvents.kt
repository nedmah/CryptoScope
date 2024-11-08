package com.example.crypto_info.presentation


sealed class CryptoInfoEvents {

    data class OnIntervalPushed(val intervals: TimeIntervals) : CryptoInfoEvents()
    data class OnFavourite(val cryptoId : String) : CryptoInfoEvents()
}