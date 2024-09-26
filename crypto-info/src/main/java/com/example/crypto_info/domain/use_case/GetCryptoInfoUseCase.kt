package com.example.crypto_info.domain.use_case

import com.example.core.util.Resource
import com.example.crypto_info.domain.model.CryptoInfo
import com.example.crypto_info.domain.repository.CryptoInfoRepository
import com.example.crypto_info.presentation.TimeIntervals
import javax.inject.Inject

class GetCryptoInfoUseCase @Inject constructor(
    private val repository: CryptoInfoRepository
) {
    suspend operator fun invoke(coinId : String, intervals: TimeIntervals) : Resource<CryptoInfo> {
        return repository.getCryptoInfo(coinId,intervals)
    }
}