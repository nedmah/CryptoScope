package com.example.cryptolisting.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.core.util.Resource
import com.example.cryptolisting.data.local.CryptoListingEntity
import com.example.cryptolisting.data.toCryptoListingsModel
import com.example.cryptolisting.domain.repository.CryptoListingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoListingViewModel @Inject constructor(
    private val repository: CryptoListingsRepository,
    private val pager: Pager<Int, CryptoListingEntity>
) : ViewModel() {


    var pageFlow = pager.flow.map {pagingData -> pagingData.map { it.toCryptoListingsModel() } }.cachedIn(viewModelScope)

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
        }
    }


    private fun getCryptoListings(
        query: String = state.searchQuery,
        fetchFromNetwork: Boolean = false
    ) {

        if (fetchFromNetwork){
            pageFlow = pager.flow.map { pagingData ->
                pagingData.map { it.toCryptoListingsModel() }
            }.cachedIn(viewModelScope)
        }else {
            viewModelScope.launch() {
                repository.getListings(fetchFromNetwork, query).collect { result ->
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
    }
}