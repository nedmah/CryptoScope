package com.example.wallet.domain.data_source

import com.example.core.domain.model.CryptoListingsModel
import com.example.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface FavoriteCoinsDatasource {

    suspend fun getMyCoinsListings() : Flow<Resource<List<CryptoListingsModel>>>

}