package com.example.crypto_info.presentation.comparison

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.CryptoListingsModel
import com.example.core.util.Resource
import com.example.core.util.calculatePercentageChange
import com.example.crypto_info.domain.use_case.GetCryptoComparisonChartUseCase
import com.example.crypto_info.domain.use_case.GetCryptoInfoChartUseCase
import com.example.crypto_info.domain.use_case.GetCryptoInfoUseCase
import com.example.crypto_info.domain.use_case.GetCryptoLocalDataUseCase
import com.example.crypto_info.domain.use_case.GetCryptoNamesUseCase
import com.example.crypto_info.presentation.TimeIntervals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import me.bytebeats.views.charts.line.LineChartData
import javax.inject.Inject

class CryptoComparisonViewModel @Inject constructor(
    private val getCryptoNamesUseCase: GetCryptoNamesUseCase,
    private val getCryptoLocalDataUseCase: GetCryptoLocalDataUseCase,
    private val getCryptoComparisonChartUseCase: GetCryptoComparisonChartUseCase
) : ViewModel() {

    private var _state = MutableStateFlow(CryptoComparisonState())
    val state = _state.asStateFlow()

    private var id1 : String = ""
    private var id2 : String = ""

    init {

    }

    fun onEvent(events: ComparisonEvents) {
        when (events) {
            ComparisonEvents.OnCompareClicked -> fetchCryptoNames()
            is ComparisonEvents.OnIntervalPushed -> getCryptoInfoCharts(id1,id2,events.intervals)
            is ComparisonEvents.OnCompareConfirmed -> {
                fetchAllData(events.name1, events.name2)
            }
        }
    }


    private fun fetchAllData(
        name1: String,
        name2: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val result1 = getCryptoLocalDataUseCase.invoke(name1)
            val result2 = getCryptoLocalDataUseCase.invoke(name2)

            val crypto1 = result1.data
            val crypto2 = result2.data

            fetchCryptoData(crypto1, crypto2)
            crypto1?.let { crypto2?.let { it1 ->
                getCryptoInfoCharts(it.cryptoId, it1.cryptoId)
                id1 = it.cryptoId
                id2 = it1.cryptoId
            } }
        }
    }


    private fun fetchCryptoData(crypto1: CryptoListingsModel?, crypto2: CryptoListingsModel?) {
        viewModelScope.launch(Dispatchers.IO) {

            val updatedDataList = mutableListOf<Pair<String, Pair<String, String>>>()
            val crypto1Data = listOf(
                "Name" to crypto1?.name.orEmpty(),
                "Price" to crypto1?.price.orEmpty(),
                "Rank" to crypto1?.rank?.toString().orEmpty(),
                "Total supply" to crypto1?.totalSupply.orEmpty(),
                "Market cap" to crypto1?.marketCap.orEmpty(),
                "Current percentage" to crypto1?.percentage.orEmpty(),
                "Percentage for hour" to crypto1?.percentageOneHour.orEmpty(),
                "Percentage for week" to crypto1?.percentageOneWeek.orEmpty()
            )

            val crypto2Data = listOf(
                "Name" to crypto2?.name.orEmpty(),
                "Price" to crypto2?.price.orEmpty(),
                "Rank" to crypto2?.rank?.toString().orEmpty(),
                "Total supply" to crypto2?.totalSupply.orEmpty(),
                "Market cap" to crypto2?.marketCap.orEmpty(),
                "Current percentage" to crypto2?.percentage.orEmpty(),
                "Percentage for hour" to crypto2?.percentageOneHour.orEmpty(),
                "Percentage for week" to crypto2?.percentageOneWeek.orEmpty()
            )

            for (i in crypto1Data.indices) {
                val key = crypto1Data[i].first
                val value = Pair(crypto1Data[i].second, crypto2Data[i].second)
                updatedDataList.add(key to value)
            }

            _state.value = _state.value.copy(
                image1Url = crypto1?.icon.orEmpty(),
                image2Url = crypto2?.icon.orEmpty(),
                cryptoData = updatedDataList
            )

        }
    }

    private fun getCryptoInfoCharts(
        coinId1: String,
        coinId2: String,
        period: TimeIntervals = TimeIntervals.ONE_DAY
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            getCryptoComparisonChartUseCase.invoke(coinId1, coinId2, period).collect { resource ->
                when (resource) {
                    is Resource.Error -> _state.value = _state.value.copy(
                        errorChart = resource.message ?: "Unknown error",
                        loading = false
                    )

                    is Resource.Loading -> _state.value = _state.value.copy(loading = resource.isLoading)
                    is Resource.Success -> {

                        val cryptoInfo1 = resource.data?.first
                        val cryptoInfo2 = resource.data?.second

                        val chartData1 = cryptoInfo1?.prices?.mapIndexed { index, price ->
                            LineChartData.Point(price, cryptoInfo1.time[index].toString())
                        }

                        val chartData2 = cryptoInfo2?.prices?.mapIndexed { index, price ->
                            LineChartData.Point(price, cryptoInfo2.time[index].toString())
                        }
                        _state.value = _state.value.copy(
                            cryptoChart1 = chartData1?.let { LineChartData(it) }
                                ?: _state.value.cryptoChart1,
                            cryptoChart2 = chartData2?.let { LineChartData(it) }
                                ?: _state.value.cryptoChart2,
                            loading = false
                        )

                    }
                }
            }
        }

    }


    @Suppress("UNCHECKED_CAST")
    private fun fetchCryptoNames() {
        viewModelScope.launch(Dispatchers.IO) {
            val names = getCryptoNamesUseCase.invoke()
            if (names.message != null) _state.value =
                _state.value.copy(cryptoNames = listOf(names.message) as List<String>)
            else names.data?.let { _state.value = _state.value.copy(cryptoNames = it) }
        }
    }


}