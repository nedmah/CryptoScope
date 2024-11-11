package com.example.cryptolisting.domain.repository

import com.example.core.util.Resource
import com.example.core.domain.model.CryptoListingsModel
import kotlinx.coroutines.flow.Flow


interface CryptoListingsRepository {

    suspend fun getListings(
        fetchFromNetwork : Boolean,
        query : String
    ) : Flow<Resource<List<CryptoListingsModel>>>
}