package com.example.wallet.data.local.data_source

import android.util.Log
import com.example.core.data.db.daos.WalletBalanceDao
import com.example.core.data.db.daos.WalletCryptoDao
import com.example.core.data.db.entities.WalletBalanceEntity
import com.example.core.data.db.entities.WalletWithDetails
import com.example.core.util.Resource
import com.example.wallet.domain.data_source.WalletBalanceDatasource
import com.example.wallet.domain.model.CryptoWalletModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WalletBalanceDatasourceImpl @Inject constructor(
    private val walletCryptoDao: WalletCryptoDao,
    private val walletBalanceDao: WalletBalanceDao
) : WalletBalanceDatasource {

    override suspend fun getWalletBalance(): Flow<Resource<CryptoWalletModel>> {
        return flow {
            val data = walletBalanceDao.getLatestWalletBalance()
            val latestBalance = data?.totalBalance?.toDouble() ?: 0.0


            walletCryptoDao.getAllWalletCryptos().collect { coinsList ->
                if (coinsList.isEmpty()) emit(
                    Resource.Error(
                        message = "Нет монет",
                        data = CryptoWalletModel("0", false, "0", "0")
                    )
                )
                else {

                    var sum = 0.0
                    val percentageList = mutableListOf<Double>()

                    coinsList.forEach { coin ->
                        val amount = coin.count.toDouble() * coin.price.toDouble()
                        sum += amount
                        percentageList.add(coin.percentage.toDouble())
                    }
                    val percentage = (percentageList.sum() / percentageList.count()).toString()
                    val profit = (sum - latestBalance).toString()
                    val unixTime = System.currentTimeMillis()

                    if (walletBalanceDao.countMatchingRecords(sum.toString(), profit) == 0) {
                        walletBalanceDao.insertWalletBalance(
                            WalletBalanceEntity(
                                totalBalance = sum.toString(),
                                profitOrAddition = profit,
                                isAddition = false,
                                percentageChange = percentage,
                                date = unixTime
                            )
                        )
                    }

                    emit(
                        Resource.Success(
                            data = CryptoWalletModel(
                                sum.toString(),
                                false,
                                profit,
                                percentage
                            )
                        )
                    )
                }
            }
        }
    }

}