package com.example.cryptolisting.presentation

sealed class CryptoListingsEvents {
    data class OnSearchQueryChange(val query : String) : CryptoListingsEvents()
    data object Refresh : CryptoListingsEvents()
    data object OnFilterIconPushed : CryptoListingsEvents()
    data object OnFilterDismiss : CryptoListingsEvents()
    data class Filter(val filter: Filters) : CryptoListingsEvents()
}