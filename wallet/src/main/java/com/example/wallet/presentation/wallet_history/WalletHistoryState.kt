package com.example.wallet.presentation.wallet_history

import androidx.compose.runtime.Immutable
import me.bytebeats.views.charts.line.LineChartData

@Immutable
data class WalletHistoryState(
    val balance : String = "",
    val percentage : String = "",
    val chartData : LineChartData? = null,
    val loading : Boolean = false,
    val error : String? = null
)