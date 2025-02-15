package com.example.wallet.presentation.wallet_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.wallet.domain.WalletRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.bytebeats.views.charts.line.LineChartData
import javax.inject.Inject

class WalletHistoryViewModel @Inject constructor(
    private val repository: WalletRepository,
) : ViewModel() {

    private var _state: MutableStateFlow<WalletHistoryState> =
        MutableStateFlow(WalletHistoryState())
    val state = _state.asStateFlow()

    val balanceHistory = repository.getPagedBalanceHistory().cachedIn(viewModelScope)

    init {
        getActualBalance()
        getChartData()
    }


    private fun getActualBalance(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getWalletData().collect{ data ->
                if (data != null) _state.value = _state.value.copy(balance = data.balance, percentage = data.percentage)
            }
        }
    }

    private fun getChartData(){
        viewModelScope.launch(Dispatchers.IO) {
            val chartData = repository.getWalletChartData()
            println(chartData)
            if (chartData == null || chartData.price.size < 2)
                _state.value = _state.value.copy(error = "Error : no chart data")
            else {
                val lineChartData = chartData.price.mapIndexed { index, price ->
                    LineChartData.Point(price, chartData.time[index])
                }
                println(LineChartData(lineChartData))
                _state.value = _state.value.copy(error = null, chartData = LineChartData(lineChartData))
            }

        }
    }

}