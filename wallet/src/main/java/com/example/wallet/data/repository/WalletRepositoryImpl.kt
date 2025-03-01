package com.example.wallet.data.repository

import android.net.http.NetworkException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.data.db.daos.BlockchainsDao
import com.example.core.data.db.daos.MyCoinsDao
import com.example.core.data.db.daos.WalletChartDao
import com.example.core.data.db.entities.BlockchainsEntity
import com.example.core.data.settings.SettingsConstants
import com.example.core.domain.settings.SettingsDataStore
import com.example.core.util.Resource
import com.example.core.util.formatPriceString
import com.example.core.util.formatPriceWithCurrency
import com.example.core.util.mapExceptionToMessage
import com.example.wallet.data.local.data_source.WalletHistoryPagingSource
import com.example.wallet.domain.model.MyCoinsModel
import com.example.wallet.data.network.WalletApi
import com.example.wallet.data.network.WalletSyncStatus
import com.example.wallet.data.toMyCoinsEntity
import com.example.wallet.data.toMyCoinsModel
import com.example.wallet.data.toWalletChartDataModel
import com.example.wallet.data.toWalletChartEntity
import com.example.wallet.domain.WalletRepository
import com.example.wallet.domain.model.BalanceHistoryModel
import com.example.wallet.domain.model.CryptoWalletModel
import com.example.wallet.domain.model.WalletChartDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.net.SocketTimeoutException
import javax.inject.Inject

class WalletRepositoryImpl @Inject constructor(
    private val myCoinsDao: MyCoinsDao,
    private val blockchainsDao: BlockchainsDao,
    private val walletChartDao: WalletChartDao,
    private val settingsDataStore: SettingsDataStore,
    private val api: WalletApi
) : WalletRepository {

    private val CACHE_DURATION = 10 * 60 * 1000L // 10 минут в миллисекундах

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun fetchMyCoins(
        address: String,
        blockchain: String?
    ): Flow<Resource<List<MyCoinsModel>>> {
        return flow {
            emit(Resource.Loading(true))

            val lastUpdated = settingsDataStore.getLong(SettingsConstants.WALLET_LAST_UPDATED)
            val currentTime = System.currentTimeMillis()

            val currencyCode = settingsDataStore.getString(SettingsConstants.CURRENCY) ?: "USD"
            val currencyRate = settingsDataStore.getDouble(SettingsConstants.CURRENCY_RATE) ?: 1.0

            val myCoinsLocal = myCoinsDao.getAllData()

            if ((lastUpdated != null && currentTime - lastUpdated < CACHE_DURATION) && myCoinsLocal.isNotEmpty()) {
                emit(Resource.Loading(false))
                emit(Resource.Success(data = myCoinsLocal.map { item -> item.toMyCoinsModel(currencyCode, currencyRate) }))
            } else try {

                val myCoins = if (blockchain != null) {
                    val response = api.getWalletBalanceWithBlockchain(address, blockchain)
                    response.map { it.toMyCoinsEntity() }
                } else {
                    val response = api.getWalletBalances(address)
                    response.flatMap { it.balances ?: emptyList() }.map { it.toMyCoinsEntity() }
                }

                myCoinsDao.clearMyCoins()
                myCoinsDao.saveMyCoins(myCoins)
                settingsDataStore.putLong(SettingsConstants.WALLET_LAST_UPDATED, currentTime)

                emit(Resource.Loading(false))
                emit(Resource.Success(data = myCoins.map { it.toMyCoinsModel(currencyCode, currencyRate) }))

            } catch (e: Exception) {
                emit(Resource.Error(message = mapExceptionToMessage(e)))
            }

        }
    }

    override suspend fun getBlockchainEntityByConnectionId(connectionId: String): BlockchainsEntity? {
        return blockchainsDao.getBlockchainById(connectionId)
    }

    override suspend fun getWalletSyncStatus(
        address: String,
        blockchain: String
    ): WalletSyncStatus {
        return try {
            val response = api.getWalletSyncStatus(address, blockchain)

            when (response.code()) {
                200 -> {
                    val body = response.body()
                    if (body != null) {
                        when (body.status) {
                            "synced" -> WalletSyncStatus.Synced
                            else -> WalletSyncStatus.NotSynced
                        }
                    } else {
                        WalletSyncStatus.Error("Empty response body")
                    }
                }
                401 -> WalletSyncStatus.Unauthorized
                409 -> WalletSyncStatus.NotSynced
                else -> WalletSyncStatus.Error("Unexpected error: ${response.code()}")
            }
        } catch (e: Exception) {
            WalletSyncStatus.Error("Network error: ${e.message}")
        }
    }

    override suspend fun saveWalletChartToDb(address: String, blockchain: String) {
        when (val status = getWalletSyncStatus(address,blockchain)){
            is WalletSyncStatus.Error -> println(status.message)
            WalletSyncStatus.NotSynced -> {
                api.syncWallet(address, blockchain)
                val walletChartsRemote = api.getWalletChart(address = address, blockchain = blockchain)
                if (walletChartsRemote.result.isNotEmpty()) {
                    walletChartDao.clearWalletChartData()
                    walletChartDao.saveWalletChartData(walletChartsRemote.toWalletChartEntity())
                }
            }
            WalletSyncStatus.Synced -> {
                val walletChartsRemote = api.getWalletChart(address = address, blockchain = blockchain)
                if (walletChartsRemote.result.isNotEmpty()) {
                    walletChartDao.clearWalletChartData()
                    walletChartDao.saveWalletChartData(walletChartsRemote.toWalletChartEntity())
                }
            }
            WalletSyncStatus.Unauthorized -> println("Unauthorized")
        }
    }


    override suspend fun getWalletData(): Flow<CryptoWalletModel?> {

        val currencyCode = settingsDataStore.getString(SettingsConstants.CURRENCY) ?: "USD"
        val currencyRate = settingsDataStore.getDouble(SettingsConstants.CURRENCY_RATE) ?: 1.0

        return walletChartDao.getLastTwoEntries()
            .map { entries ->
                if (entries.isEmpty()) {
                    null // Если записей нет, возвращаем null
                } else {
                    val latest = entries[0]
                    val previous = entries.getOrNull(1) ?: latest // Если в БД только одна запись, используем её же

                    val balance = latest.price
                    val profit = latest.price - previous.price
                    val percentageFull = if (previous.price != 0.0) (profit / previous.price) * 100 else 0.0
                    val percentage = "%.2f".format(percentageFull)
                    CryptoWalletModel(
                        formatPriceWithCurrency(balance,currencyCode, currencyRate),
                        formatPriceWithCurrency(profit, currencyCode, currencyRate),
                        percentage
                    )
                }
            }
            .catch { e ->
                println("Error fetching wallet data: ${e.message}")
                emit(null)
            }
    }

    override suspend fun getWalletChartData(): WalletChartDataModel? {
        val data = walletChartDao.getAllData()
        return data.toWalletChartDataModel()
    }

    override fun getPagedBalanceHistory(): Flow<PagingData<BalanceHistoryModel>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { WalletHistoryPagingSource(walletChartDao, settingsDataStore) }
        ).flow
    }


}