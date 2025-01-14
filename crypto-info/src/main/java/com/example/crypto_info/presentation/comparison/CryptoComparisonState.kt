package com.example.crypto_info.presentation.comparison

import androidx.compose.runtime.Immutable
import com.example.core.domain.model.CryptoListingsModel
import com.example.crypto_info.domain.model.CryptoInfo
import com.example.crypto_info.presentation.TimeIntervals
import me.bytebeats.views.charts.line.LineChartData

@Immutable
data class CryptoComparisonState(
    val image1Url: String = "",
    val image2Url: String = "",
    val cryptoChart1: LineChartData = LineChartData(listOf()),
    val cryptoChart2: LineChartData = LineChartData(listOf()),
    val cryptoData: List<Pair<String, Pair<String, String>>> = listOf(
        "Name" to Pair("",""),
        "Price" to Pair("",""),
        "Rank" to Pair("",""),
        "Total supply" to Pair("",""),
        "Market cap" to Pair("",""),
        "Current percentage" to Pair("",""),
        "Percentage for hour" to Pair("",""),
        "Percentage for week" to Pair("","")
    ),
    val cryptoNames: List<String> = emptyList(),
    val loading: Boolean = false,
    val currentInterval: TimeIntervals = TimeIntervals.ONE_DAY,
    val errorChart: String? = "",
    val error1: String? = "",
    val error2: String? = ""
)
