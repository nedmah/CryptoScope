package com.example.crypto_info.data.repository

import android.net.http.NetworkException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.example.core.util.Resource
import com.example.crypto_info.data.remote.CryptoInfoApi
import com.example.crypto_info.data.toCryptoInfo
import com.example.crypto_info.domain.model.CryptoInfo
import com.example.crypto_info.domain.repository.CryptoInfoRepository
import com.example.crypto_info.presentation.TimeIntervals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CryptoInfoRepositoryImpl @Inject constructor(
    private val api: CryptoInfoApi
): CryptoInfoRepository {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getCryptoInfo(coinId: String, period: TimeIntervals): Flow<Resource<CryptoInfo>> {
        return flow{

            emit(Resource.Loading(true))
            val cryptoInfoRemote = api.getCryptoCharts(coinId,period.text)

            val cryptoInfo = cryptoInfoRemote.toCryptoInfo()
            emit(Resource.Success(data = cryptoInfo))
            emit(Resource.Loading(false))
        }
    }
}