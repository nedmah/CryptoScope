package com.example.cryptolisting.presentation

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.example.core.domain.model.CryptoListingsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Immutable
data class CryptoListingsState(
    val listings: Flow<PagingData<CryptoListingsModel>> = emptyFlow(),
    val searchQuery: String = "",
    val sortOrder: Filters = Filters.RANK,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isBottomDialogOpened: Boolean = false,
    val error: String = ""
)
