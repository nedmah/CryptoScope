package com.example.crypto_info.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.core.data.db.daos.CryptoListingsDao
import com.example.core.data.mappers.toCryptoListingsModel
import com.example.core.domain.model.CryptoListingsModel
import com.example.core.util.Resource
import com.example.crypto_info.data.remote.CryptoInfoApi
import com.example.crypto_info.data.toCryptoInfo
import com.example.crypto_info.domain.model.CryptoInfo
import com.example.crypto_info.domain.repository.CryptoInfoRepository
import com.example.crypto_info.presentation.TimeIntervals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class CryptoInfoRepositoryImpl @Inject constructor(
    private val api: CryptoInfoApi,
    private val dao : CryptoListingsDao
) : CryptoInfoRepository {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getCryptoInfoChart(
        coinId: String,
        period: TimeIntervals
    ): Flow<Resource<CryptoInfo>> {
        return flow {

            emit(Resource.Loading(true))
            val cryptoInfoRemote = api.getCryptoCharts(coinId, period.text)

            val cryptoInfo =
                if (period == TimeIntervals.ONE_MONTH) cryptoInfoRemote.toCryptoInfo()
                else cryptoInfoRemote.filterIndexed { index, _ -> index % 2 == 0 }.toCryptoInfo()

            emit(Resource.Success(data = cryptoInfo))
            emit(Resource.Loading(false))
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getCryptoInfo(coinId: String): Flow<Resource<CryptoListingsModel>> {
        return flow {
            emit(Resource.Loading(true))

            val cryptoInfo = dao.getCryptoListingById(coinId)?.toCryptoListingsModel()
            if (cryptoInfo != null) {
                emit(Resource.Success(data = cryptoInfo))
                emit(Resource.Loading(false))
            } else {
                val remoteCryptoInfo = try {
                    api.getCryptoInfo(coinId)
                } catch (e: HttpException) {
                    e.printStackTrace()
                    emit(Resource.Error(message = "Couldn't retrieve data"))
                    null
                } catch (e: IOException) {
                    e.printStackTrace()
                    emit(Resource.Error(message = "Couldn't retrieve data"))
                    null
                }
                remoteCryptoInfo?.let {
                    emit(Resource.Success(data = remoteCryptoInfo.toCryptoListingsModel()))
                    emit(Resource.Loading(false))
                }
            }

        }
    }
}