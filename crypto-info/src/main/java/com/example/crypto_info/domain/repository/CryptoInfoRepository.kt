package com.example.crypto_info.domain.repository

import com.example.core.util.Resource
import com.example.crypto_info.domain.model.CryptoInfo
import com.example.crypto_info.presentation.TimeIntervals


interface CryptoInfoRepository {

    suspend fun getCryptoInfo(
        coinId : String,
        period : TimeIntervals
    ) : Resource<CryptoInfo>
}