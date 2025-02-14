package com.example.wallet.presentation.my_coins

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.settings.SettingsConstants
import com.example.core.domain.settings.SettingsDataStore
import com.example.core.util.Resource
import com.example.wallet.domain.WalletRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyCoinsViewModel @Inject constructor(
    private val repository: WalletRepository,
    private val settingsDataStore: SettingsDataStore
): ViewModel() {

    private var _state = MutableStateFlow(MyCoinsScreenState())
    val state = _state.asStateFlow()

    init {
        fetchMyCoins()
    }

    private fun fetchMyCoins() {
        viewModelScope.launch() {
            combine(
                settingsDataStore.getStringFlow(SettingsConstants.SELECTED_WALLET_ADDRESS),
                settingsDataStore.getStringFlow(SettingsConstants.SELECTED_BLOCKCHAIN)
            ) { address, blockchain -> address to blockchain }.collect { (address, blockchain) ->
                address?.let {
                    repository.fetchMyCoins(it, blockchain).collect { result ->
                        when (result) {
                            is Resource.Error -> _state.value = _state.value.copy(
                                error = result.message ?: "an error has occurred",
                                isLoading = false
                            )

                            is Resource.Loading -> _state.value =
                                _state.value.copy(isLoading = true)

                            is Resource.Success -> {
                                _state.value = _state.value.copy(
                                    isLoading = false,
                                    myCoins = result.data ?: emptyList()
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}