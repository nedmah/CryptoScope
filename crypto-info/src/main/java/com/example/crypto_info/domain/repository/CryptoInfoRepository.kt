package com.example.crypto_info.domain.repository

import com.example.core.domain.model.CryptoListingsModel
import com.example.core.util.Resource
import com.example.crypto_info.domain.model.CryptoInfo
import com.example.crypto_info.presentation.TimeIntervals
import kotlinx.coroutines.flow.Flow


interface CryptoInfoRepository {

    suspend fun getCryptoInfoChart(
        coinId : String,
        period : TimeIntervals
    ) : Flow<Resource<CryptoInfo>>

    suspend fun getCryptoInfo(
        coinId : String,
    ) : Flow<Resource<CryptoListingsModel>>
}