package com.example.wallet.domain

import androidx.paging.PagingData
import com.example.core.data.db.entities.BlockchainsEntity
import com.example.core.util.Resource
import com.example.wallet.data.network.WalletSyncStatus
import com.example.wallet.domain.model.BalanceHistoryModel
import com.example.wallet.domain.model.CryptoWalletModel
import com.example.wallet.domain.model.MyCoinsModel
import com.example.wallet.domain.model.WalletChartDataModel
import kotlinx.coroutines.flow.Flow

interface WalletRepository {

    fun fetchMyCoins(address : String, blockchain : String?) : Flow<Resource<List<MyCoinsModel>>>

    suspend fun getBlockchainEntityByConnectionId(connectionId : String) : BlockchainsEntity?

    suspend fun getWalletSyncStatus(address: String, blockchain: String): WalletSyncStatus

    suspend fun saveWalletChartToDb(address: String, blockchain: String)

    suspend fun getWalletData() : Flow<CryptoWalletModel?>

    suspend fun getWalletChartData() : WalletChartDataModel?

    fun getPagedBalanceHistory(): Flow<PagingData<BalanceHistoryModel>>
}