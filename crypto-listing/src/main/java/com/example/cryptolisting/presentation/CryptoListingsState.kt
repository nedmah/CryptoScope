package com.example.cryptolisting.presentation

import androidx.compose.runtime.Immutable
import com.example.cryptolisting.domain.model.CryptoListingsModel

@Immutable
data class CryptoListingsState(
    val cryptos : List<CryptoListingsModel> = emptyList(),
    val searchQuery : String = "",
    val isLoading : Boolean = false,
    val isRefreshing : Boolean = false,
    val isBottomDialogOpened : Boolean = false,
    val error : String = ""
)
