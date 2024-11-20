package com.example.crypto_info.domain.use_case

import com.example.core.domain.model.CryptoListingsModel
import com.example.core.util.Resource
import com.example.crypto_info.domain.model.CryptoInfo
import com.example.crypto_info.domain.repository.CryptoInfoRepository
import com.example.crypto_info.presentation.TimeIntervals
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCryptoInfoUseCase @Inject constructor(
    private val repository: CryptoInfoRepository
) {
    suspend operator fun invoke(coinId : String) : Flow<Resource<CryptoListingsModel>> {
        return repository.getCryptoInfo(coinId = coinId)
    }
}