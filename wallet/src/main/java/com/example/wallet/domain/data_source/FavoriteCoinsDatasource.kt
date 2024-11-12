package com.example.wallet.domain.data_source

import com.example.core.domain.model.CryptoListingsModel
import kotlinx.coroutines.flow.Flow

interface FavoriteCoinsDatasource {

    suspend fun getMyCoinsListings(
        query : String
    ) : Flow<List<CryptoListingsModel>>

}