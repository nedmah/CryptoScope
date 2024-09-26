package com.example.crypto_info.presentation

sealed class CryptoInfoEvents {

    data class OnIntervalPushed(val intervals: TimeIntervals) : CryptoInfoEvents()
    object Refresh : CryptoInfoEvents()
//    data object OneDayPushed : CryptoInfoEvents()
//    data object OneWeekPushed : CryptoInfoEvents()
//    data object OneMonthPushed : CryptoInfoEvents()
//    data object OneYearPushed : CryptoInfoEvents()
//    data object AllButtonPushed : CryptoInfoEvents()
}