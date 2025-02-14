package com.example.wallet.presentation.my_coins

import androidx.compose.runtime.Immutable
import com.example.wallet.domain.model.MyCoinsModel

@Immutable
data class MyCoinsScreenState(
    val myCoins : List<MyCoinsModel> = emptyList(),
    val isLoading : Boolean = false,
    val error : String = ""
)