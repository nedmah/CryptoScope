package com.example.crypto_info.data.repository

import android.net.http.HttpException
import android.net.http.NetworkException
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
    private val dao: CryptoListingsDao
) : CryptoInfoRepository {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getCryptoInfoChart(
        coinId: String,
        period: TimeIntervals
    ): Flow<Resource<CryptoInfo>> {
        return flow {

            try{
                emit(Resource.Loading(true))
                val cryptoInfoRemote = api.getCryptoCharts(coinId, period.text)

                val cryptoInfo = listReduction(period, cryptoInfoRemote)

                emit(Resource.Success(data = cryptoInfo))
            } catch (e : IOException){
                emit(Resource.Error(message = "io exception"))
            } catch (e : NetworkException){
                emit(Resource.Error(message = "network exception"))
            }finally {
                emit(Resource.Loading(false))
            }
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



    override suspend fun getCryptoNames(): Resource<List<String>> {
        return try {
            val names = dao.getAllNames()
            Resource.Success(data = names)
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(message = "can't retrieve coins list")
        }
    }

    override suspend fun getLocalCryptoInfoByName(name: String): Resource<CryptoListingsModel> {
        val cryptoInfo = dao.getCryptoByName(name)?.toCryptoListingsModel()
        return cryptoInfo?.let {
            Resource.Success(data = it)
        } ?: Resource.Error(message = "can't retrieve crypto data")

    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getCryptoComparisonChart(
        coinId1: String?,
        coinId2: String?,
        period: TimeIntervals
    ): Flow<Resource<Pair<CryptoInfo?, CryptoInfo?>>> {
        return flow {

            try{
                emit(Resource.Loading(true))
                val cryptoInfoRemote1 = coinId1?.let { api.getCryptoCharts(it, period.text) }
                val cryptoInfoRemote2 = coinId2?.let { api.getCryptoCharts(it, period.text) }

                val cryptoInfo1 = cryptoInfoRemote1?.let { listReduction(period, it) }
                val cryptoInfo2 = cryptoInfoRemote2?.let { listReduction(period, it) }
                emit(Resource.Success(data = Pair(cryptoInfo1, cryptoInfo2)))

            } catch (e : IOException){
                emit(Resource.Error(message = "io exception ${e.message}"))
            } catch (e : NetworkException){
                emit(Resource.Error(message = "network exception ${e.message}"))
            }catch (e: Exception) {
                emit(Resource.Error(message = "Unexpected error: ${e.message}"))
            }
            finally {
                emit(Resource.Loading(false))
            }
        }
    }

    private fun listReduction(period: TimeIntervals, data :  List<List<String>>) : CryptoInfo{
        return when(period){
            TimeIntervals.ONE_MONTH -> data.toCryptoInfo()
            TimeIntervals.ALL -> data.filterIndexed { index, _ -> index % 10 == 0 }
                .toCryptoInfo()
            else -> data.filterIndexed { index, _ -> index % 2 == 0 }
                .toCryptoInfo()
        }
    }

}