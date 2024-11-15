package com.example.wallet.presentation.wallet_history

import androidx.compose.runtime.Immutable
import com.example.wallet.domain.model.WalletHistoryModel
import me.bytebeats.views.charts.line.LineChartData

@Immutable
data class WalletHistoryState(
    val balance : String = "",
    val percentage : String = "",
    val chartData : LineChartData? = null,
    val historyData : List<WalletHistoryModel> = emptyList(),
    val loading : Boolean = false,
    val error : String? = null
)