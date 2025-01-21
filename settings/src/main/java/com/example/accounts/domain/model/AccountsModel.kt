package com.example.accounts.domain.model

data class AccountsModel(
    val address : String,
    val blockChain : String? = null,
    val imageUrl : String? = null,
    val isSelected : Boolean = false
)
