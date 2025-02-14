package com.example.currency.domain

import com.example.core.util.Resource

interface CurrencyDataSource {
    suspend fun fetchCurrencies(): Resource<List<CurrencyModel>>
}