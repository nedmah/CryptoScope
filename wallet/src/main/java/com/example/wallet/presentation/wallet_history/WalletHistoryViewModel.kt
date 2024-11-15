package com.example.wallet.presentation.wallet_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.Resource
import com.example.wallet.domain.data_source.BalanceHistoryDatasource
import com.example.wallet.domain.model.WalletHistoryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.bytebeats.views.charts.line.LineChartData
import javax.inject.Inject

class WalletHistoryViewModel @Inject constructor(
    private val balanceHistoryDatasource: BalanceHistoryDatasource
) : ViewModel() {

    private var _state: MutableStateFlow<WalletHistoryState> =
        MutableStateFlow(WalletHistoryState())
    val state = _state.asStateFlow()

    init {
        fetchData()
    }


    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            balanceHistoryDatasource.fetchBalanceHistory().collect { result ->
                when (result) {
                    is Resource.Error -> _state.value =
                        _state.value.copy(balance = "0", percentage = "0", loading = false, error = result.message)

                    is Resource.Loading -> _state.value =
                        _state.value.copy(loading = true)

                    is Resource.Success -> {
                        val currentBalance = result.data?.firstOrNull() ?: WalletHistoryModel(
                            "0",
                            "0",
                            false,
                            "0",
                            "0"
                        )

                        val chartData = LineChartData(result.data!!.map { item ->
                            LineChartData.Point(item.totalBalance.toFloatOrNull() ?: 0f, item.date)
                        })
                        _state.value =
                            _state.value.copy(
                                balance = currentBalance.totalBalance,
                                percentage = currentBalance.percentageChange,
                                historyData = result.data!!,
                                chartData = chartData,
                                loading = false
                            )
                    }
                }
            }
        }
    }

}