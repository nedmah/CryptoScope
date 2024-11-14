package com.example.wallet.data.local.data_source

import com.example.core.data.db.daos.CryptoListingsDao
import com.example.core.data.mappers.toCryptoListingsModel
import com.example.core.domain.model.CryptoListingsModel
import com.example.core.util.Resource
import com.example.wallet.domain.data_source.MyCoinsListingsDatasource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MyCoinsListingsDatasourceImpl @Inject constructor(
    private val dao : CryptoListingsDao
) : MyCoinsListingsDatasource {

    override suspend fun getMyCoinsListings(): Flow<Resource<List<CryptoListingsModel>>> {
        return flow {
            emit(Resource.Loading())
            val myCoins = dao.getCryptoListingsInWallet()
            if (myCoins.isNotEmpty()) emit(Resource.Success(myCoins.map { it.toCryptoListingsModel() }))
            else emit(Resource.Error(message = "Монеты отсутствуют"))
        }
    }

}