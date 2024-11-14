package com.example.wallet.domain.data_source

import com.example.core.data.db.entities.WalletWithDetails
import com.example.core.util.Resource
import com.example.wallet.domain.model.CryptoWalletModel
import kotlinx.coroutines.flow.Flow

interface WalletBalanceDatasource {

    suspend fun getWalletBalance() : Flow<Resource<CryptoWalletModel>>
}