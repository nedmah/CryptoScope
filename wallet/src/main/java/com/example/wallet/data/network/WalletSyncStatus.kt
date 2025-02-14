package com.example.wallet.data.network

sealed class WalletSyncStatus {
    object Synced : WalletSyncStatus()
    object Unauthorized : WalletSyncStatus()
    object NotSynced : WalletSyncStatus()
    data class Error(val message: String) : WalletSyncStatus()
}