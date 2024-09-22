package com.example.cryptolisting.presentation

sealed class CryptoListingsEvents {
    data class OnSearchQueryChange(val query : String) : CryptoListingsEvents()
    data object Refresh : CryptoListingsEvents()
}