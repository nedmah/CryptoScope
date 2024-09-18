package com.example.cryptolisting.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoListingsApi {

    @GET("/coins")
    suspend fun getCryptoListings(
        @Query("page") page : Int,
        @Query("limit") pageCount : Int
    ) : CryptoListingDto

}