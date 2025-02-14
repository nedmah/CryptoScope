package com.example.currency.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.accounts.domain.model.AccountsModel
import com.example.core.data.settings.SettingsConstants
import com.example.core.domain.settings.SettingsDataStore
import com.example.core.util.Resource
import com.example.currency.domain.CurrencyDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

class CurrencyRatesViewModel @Inject constructor(
    private val currencyDataSource: CurrencyDataSource,
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    private var _state = MutableStateFlow(CurrencyScreenState())
    val state = _state.asStateFlow()

    init {
        loadSettingsFlow()
        loadCurrencies()
    }


    private fun loadCurrencies() {
        viewModelScope.launch {
            when (val result = currencyDataSource.fetchCurrencies()) {
                is Resource.Error -> _state.value = _state.value.copy(error = result.message)
                is Resource.Loading -> TODO()
                is Resource.Success -> _state.value =
                    _state.value.copy(currencies = result.data ?: emptyList(), error = null)
            }
        }
    }

    private fun loadSettingsFlow() {
        viewModelScope.launch {
            settingsDataStore.getStringFlow(SettingsConstants.CURRENCY).collect { code ->
                _state.value =
                    _state.value.copy(selectedCurrency = code ?: "USD", error = null)
            }

        }
    }

    fun selectItem(code : String, currency : Double) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsDataStore.putString(
                SettingsConstants.CURRENCY,
                code.uppercase()
            )

            settingsDataStore.putDouble(
                SettingsConstants.CURRENCY_RATE,
                currency
            )
        }
    }
}