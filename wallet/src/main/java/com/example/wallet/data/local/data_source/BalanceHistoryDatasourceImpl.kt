package com.example.wallet.data.local.data_source

import com.example.core.data.db.daos.WalletBalanceDao
import com.example.core.util.Resource
import com.example.wallet.data.toWalletHistoryModel
import com.example.wallet.domain.data_source.BalanceHistoryDatasource
import com.example.wallet.domain.model.WalletHistoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BalanceHistoryDatasourceImpl @Inject constructor(
    private val dao : WalletBalanceDao
) : BalanceHistoryDatasource {

    override fun fetchBalanceHistory(): Flow<Resource<List<WalletHistoryModel>>> {
        return flow{
            emit(Resource.Loading())
            dao.getAllBalances().collect{ data->
                if (data.isNotEmpty()){
                    emit(Resource.Success(data = data.map { it.toWalletHistoryModel() }))
                }
                else emit(Resource.Error(message = "Данные о балансе отсутствуют"))
            }
        }
    }
}