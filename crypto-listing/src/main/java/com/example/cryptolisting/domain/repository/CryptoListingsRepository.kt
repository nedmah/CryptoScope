package com.example.cryptolisting.domain.repository

import androidx.paging.PagingData
import com.example.core.data.db.entities.CryptoListingEntity
import com.example.core.util.Resource
import com.example.core.domain.model.CryptoListingsModel
import kotlinx.coroutines.flow.Flow


interface CryptoListingsRepository {

    suspend fun saveListings(
        fetchFromNetwork : Boolean
    ) : Flow<Resource<Unit>>


    fun getPagedListings(
        query: String,
        sortOrder : String,
    ) : Flow<PagingData<CryptoListingEntity>>
}