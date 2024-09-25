package com.example.crypto_info.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface CryptoInfoApi {


    @GET("/coins/{coinId}/charts")
    suspend fun getCryptoCharts(
        @Path("coinId") coinId : String,
        @Query("period") period : String   //24h,1w,1m,3m,6m,1y,all
    )

}