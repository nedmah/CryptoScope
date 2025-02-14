package com.example.currency.presentation

import com.example.currency.domain.CurrencyModel

data class CurrencyScreenState(
    val currencies : List<CurrencyModel> = emptyList(),
    val selectedCurrency : String = "usd",
    val error: String? = null
)
