package com.example.cryptolisting.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.core.data.mappers.toCryptoListingsModel
import com.example.cryptolisting.domain.repository.CryptoListingsRepository
import com.example.core.domain.repository.FavouritesRepository
import com.example.core.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


class CryptoListingViewModel @Inject constructor(
    private val listingsRepository: CryptoListingsRepository,
    private val favouritesRepository: FavouritesRepository,
) : ViewModel() {

    private var _state = MutableStateFlow(CryptoListingsState())
    val state = _state.asStateFlow()

    private var pagingJob: Job? = null

    init {
        loadAndSaveCryptoListings()
        loadListings()
//        updateCryptoListFlow()
    }

    fun onEvent(events: CryptoListingsEvents) {
        when (events) {
            is CryptoListingsEvents.Filter -> {
                _state.value =
                    _state.value.copy(sortOrder = events.filter)
                loadListings()
            }

            is CryptoListingsEvents.OnFavourite -> toggleFavorite(events.cryptoId)

            CryptoListingsEvents.OnFilterDismiss -> _state.value =
                _state.value.copy(isBottomDialogOpened = false)

            CryptoListingsEvents.OnFilterIconPushed -> _state.value =
                _state.value.copy(isBottomDialogOpened = true)

            is CryptoListingsEvents.OnSearchQueryChange -> {
                _state.value = _state.value.copy(searchQuery = events.query)
                loadListings()
            }

            CryptoListingsEvents.Refresh -> loadAndSaveCryptoListings(true)
        }
    }

    private fun loadListings() {

        pagingJob?.cancel()

        pagingJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            delay(500L)

            val listingsFlow = listingsRepository.getPagedListings(
                query = _state.value.searchQuery,
                sortOrder = _state.value.sortOrder.value
            )
                .map { it.map { entity -> entity.toCryptoListingsModel("usd", 1.0) } }
                .cachedIn(viewModelScope)

            val favoritesFlow = favouritesRepository.getFavouritesFlow()

            val combinedFlow = combine(listingsFlow, favoritesFlow) { pagingData, favouritesList ->
                pagingData.map { crypto ->
                    crypto.copy(isFavorite = favouritesList.contains(crypto.cryptoId))
                }
            }

            _state.update { it.copy(listings = combinedFlow, isLoading = false) }
        }
    }

    private fun loadAndSaveCryptoListings(fetchFromNetwork: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            listingsRepository.saveListings(fetchFromNetwork).collect {result ->
                when(result){
                    is Resource.Error -> _state.value = _state.value.copy(error = result.message ?: "Error", isLoading = false)
                    is Resource.Loading -> _state.value = _state.value.copy(isLoading = true)
                    is Resource.Success -> _state.value = _state.value.copy(isLoading = false)
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


}