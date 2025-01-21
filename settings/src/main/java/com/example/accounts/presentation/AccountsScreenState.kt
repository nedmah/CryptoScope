package com.example.accounts.presentation

import androidx.compose.runtime.Immutable
import com.example.accounts.domain.model.AccountsModel
import com.example.accounts.domain.model.BlockchainModel

@Immutable
data class AccountsScreenState(
    val blockchains : List<BlockchainModel> = emptyList(),
    val accounts : List<AccountsModel> = emptyList(),
    val blockchainsError : String = ""
)
