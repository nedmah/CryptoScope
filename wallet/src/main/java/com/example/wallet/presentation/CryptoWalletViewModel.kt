package com.example.wallet.presentation

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.Resource
import com.example.wallet.data.local.data_source.WalletBalanceDatasourceImpl
import com.example.wallet.domain.data_source.FavoriteCoinsDatasource
import com.example.wallet.domain.data_source.MyCoinsListingsDatasource
import com.example.wallet.domain.data_source.WalletBalanceDatasource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CryptoWalletViewModel @Inject constructor(
    private val favoriteCoinsDatasource: FavoriteCoinsDatasource,
    private val myCoinsListingsDatasource: MyCoinsListingsDatasource,
    private val walletBalanceDatasource: WalletBalanceDatasource
) : ViewModel() {

    private var _state: MutableStateFlow<WalletScreenState> = MutableStateFlow(WalletScreenState())
    val state = _state.asStateFlow()

    init {
        fetchMyCoins()
        fetchFavoriteCoins()
        calculateWalletBalance()
    }



    private fun calculateWalletBalance(){
        viewModelScope.launch(Dispatchers.IO) {
            walletBalanceDatasource.getWalletBalance().collect{result ->
                when(result){
                    is Resource.Error -> _state.value = _state.value.copy(
                        wallet = result.data ?: _state.value.wallet,
                    )
                    is Resource.Loading -> TODO()
                    is Resource.Success -> _state.value = _state.value.copy(
                        wallet = result.data ?: _state.value.wallet,
                    )
                }
            }
        }
    }

    private fun fetchMyCoins() {
        viewModelScope.launch(Dispatchers.IO) {
            myCoinsListingsDatasource.getMyCoinsListings().collect { result ->

                when (result) {
                    is Resource.Error -> _state.value =
                        _state.value.copy(myCoinsLoading = false, myCoinsError = result.message)

                    is Resource.Loading -> _state.value =
                        _state.value.copy(myCoinsLoading = true)

                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            myCoins = result.data ?: emptyList(),
                            myCoinsLoading = false,
                            myCoinsError = null
                        )
                    }
                }

            }
        }
    }

    private fun fetchFavoriteCoins() {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteCoinsDatasource.getMyCoinsListings().collect { result ->

                when (result) {
                    is Resource.Error -> _state.value =
                        _state.value.copy(favouriteCoinsLoading = false, favouriteCoinsError = result.message)

                    is Resource.Loading -> _state.value =
                        _state.value.copy(favouriteCoinsLoading = true)

                    is Resource.Success -> _state.value = _state.value.copy(
                        favouriteCoins = result.data ?: emptyList(),
                        favouriteCoinsLoading = false,
                        favouriteCoinsError = null
                    )
                }

            }
        }
    }

}