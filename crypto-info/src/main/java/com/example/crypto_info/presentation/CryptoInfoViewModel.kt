package com.example.crypto_info.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.Resource
import com.example.core.util.calculatePercentageChange
import com.example.crypto_info.domain.model.CryptoInfo
import com.example.crypto_info.domain.use_case.GetCryptoInfoUseCase
import com.example.cryptolisting.domain.model.CryptoListingsModel
import com.example.cryptolisting.domain.repository.FavouritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoInfoViewModel @Inject constructor(
    private val getCryptoInfoUseCase: GetCryptoInfoUseCase,
    private val repository: FavouritesRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private var _state = MutableStateFlow(CryptoInfoState())
    val state = _state.asStateFlow()

    private val cryptoListingsModel: CryptoListingsModel? =
        savedStateHandle.get<CryptoListingsModel>("cryptoInfo")

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
            getCryptoInfo(coinId = model.cryptoId)
        }
        }

    fun onEvent(events: CryptoInfoEvents){
        when (events){
            is CryptoInfoEvents.OnIntervalPushed -> {
                cryptoListingsModel?.let { model ->
                    getCryptoInfo(
                        coinId = model.cryptoId,
                        period = events.intervals
                    )
                }
            }
            CryptoInfoEvents.Refresh -> TODO()
            is CryptoInfoEvents.OnFavourite -> toggleFavorite(events.cryptoId)
        }
    }



    private fun getCryptoInfo(period : TimeIntervals = TimeIntervals.ONE_DAY, coinId : String){
        viewModelScope.launch(Dispatchers.IO) {
            getCryptoInfoUseCase(coinId,period).collect{ result ->
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
        }
    }
}