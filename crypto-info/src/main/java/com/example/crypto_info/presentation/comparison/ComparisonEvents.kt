package com.example.crypto_info.presentation.comparison

import com.example.crypto_info.presentation.TimeIntervals

sealed class ComparisonEvents {
    data class OnIntervalPushed(val intervals: TimeIntervals) : ComparisonEvents()
    data object OnCompareClicked : ComparisonEvents()
    data class OnCompareConfirmed(val name1 : String, val name2 : String) : ComparisonEvents()
}