package com.example.wallet.data.local.data_source

import com.example.core.data.db.daos.CryptoListingsDao
import com.example.core.data.mappers.toCryptoListingsModel
import com.example.core.domain.model.CryptoListingsModel
import com.example.core.util.Resource
import com.example.wallet.domain.data_source.FavoriteCoinsDatasource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteCoinsDatasourceImpl @Inject constructor(
    private val dao : CryptoListingsDao
) : FavoriteCoinsDatasource {

    override suspend fun getMyCoinsListings(): Flow<Resource<List<CryptoListingsModel>>> {
        return flow {
            emit(Resource.Loading())
            dao.getCryptoListingsInFavorites().collect{
                val myCoins = it
                if (myCoins.isNotEmpty()) emit(Resource.Success(myCoins.map { it.toCryptoListingsModel() }))
                else emit(Resource.Error(message = "Монеты отсутствуют"))
            }
        }
    }

}