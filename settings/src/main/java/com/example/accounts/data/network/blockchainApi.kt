package com.example.accounts.data.network

import retrofit2.http.GET


interface blockchainApi {

    @GET("/wallet/blockchains")
    suspend fun getBlockchains() : List<BlockchainsDto>
}