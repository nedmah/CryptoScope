package com.example.cryptolisting.presentation

import com.example.cryptolisting.domain.model.CryptoListingsModel


data class CryptoListingsState(
    val isLoading : Boolean = false,
    val isRefreshing : Boolean = false,
    val error : String = "",
    val cryptos : List<CryptoListingsModel> = emptyList(),
    val searchQuery : String = ""
)
