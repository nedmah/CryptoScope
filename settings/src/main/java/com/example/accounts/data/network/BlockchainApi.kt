package com.example.accounts.data.network

import retrofit2.http.GET


interface BlockchainApi {

    @GET("/wallet/blockchains")
    suspend fun getBlockchains() : List<BlockchainsDto>
}