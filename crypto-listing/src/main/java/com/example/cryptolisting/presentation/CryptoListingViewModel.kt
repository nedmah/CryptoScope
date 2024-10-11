package com.example.cryptolisting.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.core.util.Resource
import com.example.core.db.entities.CryptoListingEntity
import com.example.cryptolisting.data.toCryptoListingsModel
import com.example.cryptolisting.domain.repository.CryptoListingsRepository
import com.example.cryptolisting.domain.repository.FavouritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoListingViewModel @Inject constructor(
    private val listingsRepository: CryptoListingsRepository,
    private val favouritesRepository: FavouritesRepository,
) : ViewModel() {

    var state by mutableStateOf(CryptoListingsState())

    private var searchJob: Job? = null

    init {
        getCryptoListings()
    }

    fun onEvent(event: CryptoListingsEvents) {
        when (event) {

            is CryptoListingsEvents.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getCryptoListings()
                }
            }

            CryptoListingsEvents.Refresh -> getCryptoListings(fetchFromNetwork = true)

            is CryptoListingsEvents.Filter -> {
                state = state.copy(
                    cryptos = when(event.filter){
                        Filters.RANK -> state.cryptos.sortedBy { it.rank }
                        Filters.PRICE_INC -> state.cryptos.sortedBy { it.price.toDouble() }
                        Filters.PRICE_DEC -> state.cryptos.sortedByDescending { it.price.toDouble() }
                        Filters.PERCENTAGE_INC -> state.cryptos.sortedBy { it.percentage.toDouble() }
                        Filters.PERCENTAGE_DEC -> state.cryptos.sortedByDescending { it.percentage.toDouble() }
                        Filters.NAME -> state.cryptos.sortedBy { it.name }
                    }
                )
            }
            CryptoListingsEvents.OnFilterIconPushed -> state = state.copy(isBottomDialogOpened = true)
            CryptoListingsEvents.OnFilterDismiss -> state= state.copy(isBottomDialogOpened = false)

            is CryptoListingsEvents.OnFavourite -> toggleFavorite(event.cryptoId)
            CryptoListingsEvents.CheckFavourites -> viewModelScope.launch(Dispatchers.IO) { updateCryptoList() }
        }
    }



    private fun getCryptoListings(
        query: String = state.searchQuery,
        fetchFromNetwork: Boolean = false
    ) {
            viewModelScope.launch() {
                listingsRepository.getListings(fetchFromNetwork, query).collect { result ->
                    when (result) {
                        is Resource.Error -> {
                            state = state.copy(
                                error = "An error has occured"
                            )
                        }

                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }

                        is Resource.Success -> {
                            updateCryptoList()
                            state = state.copy(
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
            if (isFavourite) {
                favouritesRepository.removeFavourite(cryptoId)
            } else {
                favouritesRepository.addFavourite(cryptoId)
            }
            updateCryptoList()
        }
    }

    private suspend fun updateCryptoList() {
        val favoriteIds = favouritesRepository.getFavourites()
        Log.d("TAG", "updateCryptoList: $favoriteIds")
        val updatedCryptos = state.cryptos.map { crypto ->
            crypto.copy(isFavorite = favoriteIds.contains(crypto.cryptoId))
        }
        state = state.copy(cryptos = updatedCryptos)
    }
}