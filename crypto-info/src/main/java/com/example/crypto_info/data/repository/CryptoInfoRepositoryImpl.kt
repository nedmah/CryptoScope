package com.example.crypto_info.data.repository

import android.net.http.NetworkException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.example.core.util.Resource
import com.example.crypto_info.data.remote.CryptoInfoApi
import com.example.crypto_info.data.toCryptoInfoModel
import com.example.crypto_info.domain.model.CryptoInfo
import com.example.crypto_info.domain.repository.CryptoInfoRepository
import com.example.crypto_info.presentation.TimeIntervals
import javax.inject.Inject

class CryptoInfoRepositoryImpl @Inject constructor(
    private val api: CryptoInfoApi
): CryptoInfoRepository {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getCryptoInfo(coinId: String, period: TimeIntervals): Resource<CryptoInfo> {
        return try {
            val cryptoInfoRemote = api.getCryptoCharts(coinId,period.text)
            val cryptoInfo = cryptoInfoRemote.toCryptoInfoModel()
            Resource.Success(data = cryptoInfo)
        }catch (e : NetworkException){
            Resource.Error(message = e.message ?: "network problem")
        }
    }
}