package com.example.crypto_info.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.core.util.Resource
import com.example.core.util.calculatePercentageChange
import com.example.crypto_info.domain.use_case.GetCryptoInfoChartUseCase
import com.example.core.domain.model.CryptoListingsModel
import com.example.core.domain.repository.FavouritesRepository
import com.example.crypto_info.domain.use_case.GetCryptoInfoUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CryptoInfoViewModel (
    private val cryptoListingsModel: CryptoListingsModel?,
    private val getCryptoInfoChartUseCase: GetCryptoInfoChartUseCase,
    private val getCryptoInfoUseCase : GetCryptoInfoUseCase,
    private val repository: FavouritesRepository,
): ViewModel() {

    private var _state = MutableStateFlow(CryptoInfoState())
    val state = _state.asStateFlow()

    init {
        cryptoListingsModel?.let { model ->
            _state.value = _state.value.copy(
                name = model.name,
                price = model.price,
                icon = model.icon,
                percentage = model.percentageOneHour,
                totalSupply = model.totalSupply,
                marketCap = model.marketCap,
                redditUrl = model.redditUrl,
                twitterUrl = model.twitterUrl,
                cryptoId = model.cryptoId,
                isFavourite = model.isFavorite
            )
            getCryptoInfoChart(coinId = model.cryptoId)
        }

        }

    fun onEvent(events: CryptoInfoEvents){
        when (events){
            is CryptoInfoEvents.OnIntervalPushed -> {
                cryptoListingsModel?.let { model ->
                    getCryptoInfoChart(
                        coinId = model.cryptoId,
                        period = events.intervals
                    )
                }
            }
            is CryptoInfoEvents.OnFavourite -> toggleFavorite(events.cryptoId)
        }
    }


    private fun getCryptoInfo(coinId: String) {  // if there's noname crypto from cloud wallet
        viewModelScope.launch(Dispatchers.IO) {
            getCryptoInfoUseCase(coinId).collect { result ->
                when (result) {
                    is Resource.Error -> _state.value =
                        _state.value.copy(error = result.message ?: "an error has occurred")

                    is Resource.Loading -> _state.value =
                        _state.value.copy(loading = result.isLoading)

                    is Resource.Success -> {
                        result.data?.let { data ->
                            _state.value = _state.value.copy(
                                name = data.name,
                                price = data.price,
                                icon = data.icon,
                                percentage = data.percentageOneHour,
                                totalSupply = data.totalSupply,
                                marketCap = data.marketCap,
                                redditUrl = data.redditUrl,
                                twitterUrl = data.twitterUrl,
                                cryptoId = data.cryptoId,
                                isFavourite = data.isFavorite
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getCryptoInfoChart(period : TimeIntervals = TimeIntervals.ONE_DAY, coinId : String){
        viewModelScope.launch(Dispatchers.IO) {
            getCryptoInfoChartUseCase(coinId,period).collect{ result ->
                when(result){
                    is Resource.Error -> _state.value = _state.value.copy(error = result.message?: "an error has occurred")
                    is Resource.Loading -> _state.value = _state.value.copy(loading = result.isLoading)
                    is Resource.Success -> {
                        result.data?.let { data ->
                            _state.value = _state.value.copy(
                                cryptoInfo = data,
                                percentage = calculatePercentageChange(data.prices)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun toggleFavorite(cryptoId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val isFavourite = repository.isFavourite(cryptoId)
            if (isFavourite) {
                repository.removeFavourite(cryptoId)
            } else {
                repository.addFavourite(cryptoId)
            }
            _state.value = _state.value.copy(isFavourite = !isFavourite)
        }
    }

    class CryptoInfoViewModelFactory@AssistedInject constructor(
        @Assisted private val cryptoListingsModel: CryptoListingsModel,
        private val getCryptoInfoChartUseCase: GetCryptoInfoChartUseCase,
        private val getCryptoInfoUseCase: GetCryptoInfoUseCase,
        private val repository: FavouritesRepository
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == CryptoInfoViewModel::class.java)
            return CryptoInfoViewModel(cryptoListingsModel, getCryptoInfoChartUseCase, getCryptoInfoUseCase, repository) as T
        }

        @AssistedFactory
        interface Factory {
            fun create(@Assisted cryptoListingsModel: CryptoListingsModel): CryptoInfoViewModelFactory
        }
    }

}

