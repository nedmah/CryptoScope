package com.example.crypto_info.presentation.crypto_info

import com.example.crypto_info.presentation.TimeIntervals


sealed class CryptoInfoEvents {
    data class OnIntervalPushed(val intervals: TimeIntervals) : CryptoInfoEvents()
    data class OnFavourite(val cryptoId : String) : CryptoInfoEvents()
}