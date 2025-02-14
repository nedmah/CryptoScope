package com.example.wallet.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.settings.SettingsConstants
import com.example.core.domain.settings.SettingsDataStore
import com.example.core.util.Resource
import com.example.core.util.formatPriceString
import com.example.core.util.formatPriceWithCurrency
import com.example.wallet.domain.data_source.FavoriteCoinsDatasource
import com.example.wallet.domain.WalletRepository
import com.example.wallet.domain.model.CryptoWalletModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

class CryptoWalletViewModel @Inject constructor(
    private val favoriteCoinsDatasource: FavoriteCoinsDatasource,
    private val repository: WalletRepository,
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    private var _state: MutableStateFlow<WalletScreenState> = MutableStateFlow(WalletScreenState())
    val state = _state.asStateFlow()

    init {
        fetchFavoriteCoins()
        fetchMyCoins()
    }


    private fun fetchMyCoins() {
        viewModelScope.launch() {
            combine(
                settingsDataStore.getStringFlow(SettingsConstants.SELECTED_WALLET_ADDRESS),
                settingsDataStore.getStringFlow(SettingsConstants.SELECTED_BLOCKCHAIN)
            ) { address, blockchain -> address to blockchain }
                .distinctUntilChanged()
                .collectLatest { (address, blockchain) ->
                    address?.let {
                        repository.fetchMyCoins(it, blockchain).collect { result ->
                            when (result) {
                                is Resource.Error -> _state.value = _state.value.copy(
                                    myCoinsError = result.message,
                                    myCoinsLoading = false
                                )

                                is Resource.Loading -> _state.value =
                                    _state.value.copy(myCoinsLoading = true, myCoinsError = null)

                                is Resource.Success -> {
                                    if (blockchain != null) {
                                        getBlockchainInfo(blockchain)
                                        loadAndSaveWalletChartData(address, blockchain)
                                    }
                                    _state.value = _state.value.copy(
                                        myCoinsLoading = false,
                                        currentAddress = it,
                                        myCoinsError = null,
                                        myCoins = result.data ?: emptyList()
                                    )
                                }
                            }
                        }
                        getWalletData()
                    }
                }
        }
    }

    private fun getBlockchainInfo(connectionId: String) {
        viewModelScope.launch {
            val blockchain = repository.getBlockchainEntityByConnectionId(connectionId)
            _state.value = _state.value.copy(
                currentBlockchain = blockchain.name,
                currentBlockchainImage = blockchain.icon
            )
        }
    }

    private fun loadAndSaveWalletChartData(address: String, blockchain: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveWalletChartToDb(address, blockchain)
        }
    }

    private fun getWalletData() {
        viewModelScope.launch(Dispatchers.IO) {

            val currencyCode = settingsDataStore.getString(SettingsConstants.CURRENCY) ?: "USD"
            val currencyRate = settingsDataStore.getDouble(SettingsConstants.CURRENCY_RATE) ?: 1.0

            repository.getWalletData().collect { data ->
                if (data != null) _state.value = _state.value.copy(wallet = data)
                else
                    if (_state.value.myCoins.isNotEmpty()) {
                        val sum = _state.value.myCoins.sumOf { coin ->
                            coin.price.filter { it.isDigit() || it == '.' }.toDouble() * coin.amount
                        }
                        _state.value = _state.value.copy(
                            wallet = CryptoWalletModel(
                                formatPriceWithCurrency(sum, currencyCode, currencyRate),
                                "",
                                ""
                            )
                        )
                    }
            }
        }
    }

    private fun fetchFavoriteCoins() {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteCoinsDatasource.getMyCoinsListings().collect { result ->
                _state.value = _state.value.copy(
                    favouriteCoins = result,
                    favouriteCoinsError = null
                )
            }
        }
    }

}