package com.example.cryptolisting.presentation

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.Resource
import com.example.core.domain.model.CryptoListingsModel
import com.example.cryptolisting.domain.repository.CryptoListingsRepository
import com.example.core.domain.repository.FavouritesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class CryptoListingViewModel @Inject constructor(
    private val listingsRepository: CryptoListingsRepository,
    private val favouritesRepository: FavouritesRepository,
) : ViewModel() {

    private var _state = MutableStateFlow(CryptoListingsState())
    val state = _state.asStateFlow()

    private var searchJob: Job? = null

    init {
        getCryptoListings()
        updateCryptoListFlow()
    }

    fun onEvent(event: CryptoListingsEvents) {
        when (event) {

            is CryptoListingsEvents.OnSearchQueryChange -> {
                _state.value = _state.value.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getCryptoListings()
                }
            }

            CryptoListingsEvents.Refresh -> getCryptoListings(fetchFromNetwork = true)

            is CryptoListingsEvents.Filter -> {
                _state.value = _state.value.copy(
                    cryptos = when(event.filter){
                        Filters.RANK -> _state.value.cryptos.sortedBy { it.rank }
                        Filters.PRICE_INC -> _state.value.cryptos.sortedBy { it.price.toDouble() }
                        Filters.PRICE_DEC -> _state.value.cryptos.sortedByDescending { it.price.toDouble() }
                        Filters.PERCENTAGE_INC -> _state.value.cryptos.sortedBy { it.percentage.toDouble() }
                        Filters.PERCENTAGE_DEC -> _state.value.cryptos.sortedByDescending { it.percentage.toDouble() }
                        Filters.NAME -> _state.value.cryptos.sortedBy { it.name }
                    }
                )
            }
            CryptoListingsEvents.OnFilterIconPushed -> _state.value = _state.value.copy(isBottomDialogOpened = true)
            CryptoListingsEvents.OnFilterDismiss -> _state.value = _state.value.copy(isBottomDialogOpened = false)

            is CryptoListingsEvents.OnFavourite -> toggleFavorite(event.cryptoId)
        }
    }

    fun navigateWithBundle(
        model: CryptoListingsModel,
        navigate: (Bundle) -> Unit
    ) {
        val bundle = Bundle().apply {
            putParcelable("cryptoInfo", model)
        }
        navigate(bundle)
    }



    private fun getCryptoListings(
        query: String = _state.value.searchQuery,
        fetchFromNetwork: Boolean = false
    ) {
            viewModelScope.launch() {
                listingsRepository.getListings(fetchFromNetwork, query).collect { result ->
                    when (result) {
                        is Resource.Error -> {
                            _state.value = _state.value.copy(
                                error = "An error has occured"
                            )
                        }

                        is Resource.Loading -> {
                            _state.value = _state.value.copy(isLoading = result.isLoading)
                        }

                        is Resource.Success -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                isRefreshing = false,
                                cryptos = result.data ?: emptyList()
                            )
                        }
                    }
            }
        }
    }

    private fun toggleFavorite(cryptoId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val isFavourite = favouritesRepository.isFavourite(cryptoId)

            if (isFavourite) favouritesRepository.removeFavourite(cryptoId)
            else favouritesRepository.addFavourite(cryptoId)
        }
    }


    private fun updateCryptoListFlow() {
        viewModelScope.launch {
            favouritesRepository.getFavouritesFlow().collect{ idList ->

                val updatedCryptos = _state.value.cryptos.map { crypto ->
                    crypto.copy(isFavorite = idList.contains(crypto.cryptoId))
                }
                _state.value = _state.value.copy(cryptos = updatedCryptos)
            }

        }
    }

}