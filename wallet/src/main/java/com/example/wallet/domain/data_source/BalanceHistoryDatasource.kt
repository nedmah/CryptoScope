package com.example.wallet.domain.data_source

import com.example.core.util.Resource
import com.example.wallet.R
import com.example.wallet.domain.model.WalletHistoryModel
import kotlinx.coroutines.flow.Flow

interface BalanceHistoryDatasource {

    fun fetchBalanceHistory() : Flow<Resource<List<WalletHistoryModel>>>
}