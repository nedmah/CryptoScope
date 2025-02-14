package com.example.wallet.data.network

import com.example.wallet.data.network.dto.WalletChartDto
import com.example.wallet.data.network.dto.WalletCoinDataDto
import com.example.wallet.data.network.dto.WalletCoinDto
import com.example.wallet.data.network.dto.WalletSyncStatusDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query

interface WalletApi {

    @GET("/wallet/balances")
    suspend fun getWalletBalances(
        @Query("address") address : String,
        @Query("networks") networks : String = "all"
    ) : List<WalletCoinDataDto>


    @GET("/wallet/balance")
    suspend fun getWalletBalanceWithBlockchain(
        @Query("address") address : String,
        @Query("connectionId") blockchain : String
    ) : List<WalletCoinDto>

    @GET("wallet/status")
    suspend fun getWalletSyncStatus(
        @Query("address") address : String,
        @Query("connectionId") blockchain : String
    ) : Response<WalletSyncStatusDto>

    @PATCH("wallet/transactions")
    suspend fun syncWallet(
        @Query("address") address : String,
        @Query("connectionId") blockchain : String
    ): Response<Unit>

    @GET("wallet/chart")
    suspend fun getWalletChart(
        @Query("type") period : String = "1y",
        @Query("address") address : String,
        @Query("connectionId") blockchain : String
    ) : WalletChartDto
}