package com.example.crypto_info.presentation

import com.example.cryptolisting.presentation.CryptoListingsEvents

sealed class CryptoInfoEvents {

    data class OnIntervalPushed(val intervals: TimeIntervals) : CryptoInfoEvents()
    data class OnFavourite(val cryptoId : String) : CryptoInfoEvents()
    object Refresh : CryptoInfoEvents()
}