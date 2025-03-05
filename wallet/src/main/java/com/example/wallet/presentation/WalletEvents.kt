package com.example.wallet.presentation

sealed class WalletEvents {
    data object onRefresh : WalletEvents()
}